package com.example.le_androidapp.domain

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.finaldb.data.Device
import com.example.finaldb.data.Mode
import com.example.finaldb.data.User
import com.example.finaldb.data.toModesEntity
import com.example.finaldb.data.toReadByDeviceEntity
import com.example.finaldb.data.toUserEntity
import com.example.finaldb.entities.Modes
import com.example.finaldb.entities.ReadByDevice
import com.example.finaldb.repository.ModeRepository
import com.example.finaldb.repository.ReadByDeviceRepository
import com.example.finaldb.repository.ResultRepository
import com.example.finaldb.repository.UserRepository
import com.example.le_androidapp.data.ConnectionState
import com.example.le_androidapp.data.DeviceReceiveManager
import com.example.le_androidapp.data.DeviceResult
import com.example.le_androidapp.util.Constants.MOCK_USER
import com.example.le_androidapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    val deviceReceiveManager: DeviceReceiveManager,
    private val readByDeviceRepository: ReadByDeviceRepository,
    modeRepository: ModeRepository,
) : ViewModel() {

    var initializingMessage by mutableStateOf<String?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var pitch by mutableStateOf(0f)
        private set

    var roll by mutableStateOf(0f)
        private set

    var flex by mutableStateOf(0f)
        private set

    // var connectionState by mutableStateOf<ConnectionState>(ConnectionState.Uninitialized)

    private val _deviceState = MutableStateFlow(DeviceViewModelState())
    val deviceState = _deviceState

    private val _deviceEvent = MutableSharedFlow<Unit>()
    val deviceEvent = _deviceEvent

    private val readByDevice = readByDeviceRepository.getReadyByDevice(
        MOCK_USER
    ).onEach {readByDevice ->
        Log.e("readByDevice","$readByDevice")
        _deviceState.update {
            it.copy(
                readByDevice = readByDevice
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null
    )

    val modeState = modeRepository.getModesByUser(MOCK_USER)
        .onEach { mode ->

            Log.e("modes","$mode")
            _deviceState.update {
                it.copy(
                    mode = mode
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            null)

    private val deviceManager = deviceReceiveManager.data.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        ConnectionState.Uninitialized
    )

    init {
        viewModelScope.launch {
            deviceReceiveManager.data.collect(::deviceManagerChanges)
        }
        subscribeToChanges()
    }

    fun deviceManagerChanges(result : Resource<DeviceResult>) {
        Log.e("deviceManagerChanges","$result")
        when (result) {
            is Resource.Success -> {
                _deviceState.update {
                    it.copy(
                        connectionState = result.data.connectionState
                    )
                }

                pitch = result.data.pitch
                roll = result.data.roll
                flex = result.data.flex

                viewModelScope.launch {
                    readByDeviceRepository.insertReadyByDevice(
                        Device(
                            userId = 0,
                            modeId = 0,
                            badPostureCount = incrementBadPosture(result.data.connectionState)
                                ?: 0,
                            calibratedFlexSensor = result.data.flex,
                            calibratedPitch = result.data.pitch,
                            calibratedRoll = result.data.roll,
                            recordedTime = LocalDateTime.now(),
                            mode = true
                        ).toReadByDeviceEntity()
                    )

                    _deviceEvent.emit(Unit)
                }
            }

            is Resource.Loading -> {
                initializingMessage = result.message
                _deviceState.update {
                    it.copy(
                        connectionState = ConnectionState.CurrentlyInitializing
                    )
                }
            }

            is Resource.Error -> {
                errorMessage = result.errorMessage
                _deviceState.update {
                    it.copy(
                        connectionState = ConnectionState.Uninitialized
                    )
                }
            }
        }
    }

    fun subscribeToChanges() {
        viewModelScope.launch {
            readByDevice.collect()
            modeState.collect()
            deviceReceiveManager.data.collect { result ->
                Log.e("deviceReceiveManager","$result")
                when (result) {
                    is Resource.Success -> {
                        _deviceState.update {
                            it.copy(
                                connectionState = result.data.connectionState
                            )
                        }

                        pitch = result.data.pitch
                        roll = result.data.roll
                        flex = result.data.flex

                        readByDeviceRepository.insertReadyByDevice(
                            Device(
                                userId = 0,
                                modeId = 0,
                                badPostureCount = incrementBadPosture(result.data.connectionState)
                                    ?: 0,
                                calibratedFlexSensor = result.data.flex,
                                calibratedPitch = result.data.pitch,
                                calibratedRoll = result.data.roll,
                                recordedTime = LocalDateTime.now(),
                                mode = true
                            ).toReadByDeviceEntity()
                        )

                        _deviceEvent.emit(Unit)
                    }

                    is Resource.Loading -> {
                        initializingMessage = result.message
                        _deviceState.update {
                            it.copy(
                                connectionState = ConnectionState.CurrentlyInitializing
                            )
                        }
                    }

                    is Resource.Error -> {
                        errorMessage = result.errorMessage
                        _deviceState.update {
                            it.copy(
                                connectionState = ConnectionState.Uninitialized
                            )
                        }
                    }
                }
            }
        }
    }

    fun dummyIncrement() {
        viewModelScope.launch {
            Log.e("insert","dummy")
                readByDeviceRepository.insertReadyByDevice(
                    Device(
                        userId = MOCK_USER.userId,
                        modeId = 0,
                        badPostureCount = _deviceState.value.readByDevice?.badPostureCount?.inc() ?:0,
                        calibratedFlexSensor = 14f,
                        calibratedPitch = 12f,
                        calibratedRoll = 1f,
                        recordedTime = LocalDateTime.now(),
                        mode = true
                    ).toReadByDeviceEntity()
                )
        }
    }
    private fun incrementBadPosture(connectionState: ConnectionState): Int? {
        return if (connectionState is ConnectionState.Connected) {
            val minRoll = when (_deviceState.value.mode.workingMode){
                true -> -8
                else -> -4
            }
            val maxRoll = -14
            val maxFlex = -425

            if (connectionState.yVal >= minRoll || connectionState.yVal <= maxRoll && connectionState.zVal >= maxFlex) {
                _deviceState.value.readByDevice?.badPostureCount?.inc()
            } else {
                _deviceState.value.readByDevice?.badPostureCount
            }
        } else _deviceState.value.readByDevice?.badPostureCount
    }

    fun disconnect() {
        deviceReceiveManager.disconnect()
    }

    fun reconnect() {
        deviceReceiveManager.reconnect()
    }

    fun initializeConnection() {
        errorMessage = null
        deviceReceiveManager.startReceiving()
    }

    override fun onCleared() {
        super.onCleared()
        deviceReceiveManager.closeConnection()
    }

    data class DeviceViewModelState(
        val readByDevice: ReadByDevice? = null,
        val connectionState: ConnectionState = ConnectionState.Uninitialized,
        val mode : Modes = Mode(
            workingMode = false,
            restingMode = false,
            userId = MOCK_USER.userId,
            resultId = 0
        ).toModesEntity()
    )

}