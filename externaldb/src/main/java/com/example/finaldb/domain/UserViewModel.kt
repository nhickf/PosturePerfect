package com.example.finaldb.domain

import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import com.example.finaldb.data.Device
import com.example.finaldb.data.Mode
import com.example.finaldb.data.User
import com.example.finaldb.data.toUserEntity
import com.example.finaldb.di.SimpleDependencyInjection
import com.example.finaldb.entities.UserInfo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.finaldb.data.Result
import com.example.finaldb.data.toModesEntity
import com.example.finaldb.data.toReadByDeviceEntity
import com.example.finaldb.data.toUserResultEntity
import com.example.finaldb.entities.Modes
import com.example.finaldb.entities.ReadByDevice
import com.example.finaldb.entities.UserResult
import com.example.finaldb.entities.relations.InfoAndReadyByDevice
import com.example.finaldb.entities.relations.InfoAndResult
import com.example.finaldb.entities.relations.InfoWithModes
import com.example.finaldb.entities.relations.ModesAndResultsRef
import com.example.finaldb.entities.relations.ReadByDeviceWithResultRef
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.ZoneId

class UserViewModel(private val repository: SimpleDependencyInjection.Repositories) : ViewModel() {

    init {
        Log.d("UserViewModel", "New UserViewModel instance created")
    }

    private val _mainState : MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    val userInfo : StateFlow<List<UserInfo>> = repository.userRepository.getAllUsers().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )

    val modes : StateFlow<List<Modes>> = repository.modeRepository.getAllModes().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )

    val results : StateFlow<List<UserResult>> = repository.resultRepository.getAllResults().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )

    val readByDevices : StateFlow<List<ReadByDevice>> = repository.readByDeviceRepository.getAllReadyByDevice().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )

    fun insert(user: User) = viewModelScope.launch {
        repository.userRepository.insertUserInfo(user.toUserEntity())
    }

    fun insertResult(result: Result) = viewModelScope.launch {
        repository.resultRepository.insertResult(result.toUserResultEntity())
    }

    fun insertReadByDevice(device: Device) = viewModelScope.launch {
        repository.readByDeviceRepository.insertReadyByDevice(device.toReadByDeviceEntity())
    }

    fun insertMode(mode:Mode) = viewModelScope.launch {
        repository.modeRepository.insertMode(mode.toModesEntity())
    }

    //need to insert a reference first before you can fetch the many to many relationship
    fun insertReference() = viewModelScope.launch {
        repository.modeRepository.insertReference(ModesAndResultsRef(
            1,1
        ))

        repository.readByDeviceRepository.insertReference(ReadByDeviceWithResultRef(1,1))
    }


    fun refreshStates() = viewModelScope.launch {
        insertReference()

        Log.e("relation 121","${repository.userRepository.getInfoAndResult()}")
        Log.e("relation 121","${repository.userRepository.getInfoAndReadByDevice()}")

        Log.e("relation 12M","${repository.userRepository.getInfoAndModes()}")
        Log.e("relation 12M","${repository.modeRepository.getModesAndReadByDevice()}")

        Log.e("relation M2M ","${repository.modeRepository.getModesAndResults()}")
        Log.e("relation M2M ","${repository.readByDeviceRepository.getReadyByDeviceWithResults()}")

    }

    fun insertDummies () {
        insertMode(Mode(
            workingMode = true,
            restingMode = true,
            resultId = 1,
            userId = 1
        ))

        insertResult(
            Result(
            totalTime = 5000L,
            totalBadPostureCount = 123,
            calibratedZ = 2F,
            calibratedY = 1F,
            calibratedX = 3f,
            userId = 1,
            mode = true
        )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            insertReadByDevice(
                Device(
                userId = 1,
                modeId = 1,
                mode = true,
                badPostureCount = 123,
                calibratedRoll = 3F,
                calibratedPitch = 2f,
                calibratedFlexSensor = 5f,
                recordedTime = LocalDateTime.now()
            )
            )
        }
    }

}

class UserViewModelFactory(private val repository: SimpleDependencyInjection.Repositories) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}

data class MainState(
    val userResults : List<InfoAndResult> = emptyList(),
    val userReadByDevice : List<InfoAndReadyByDevice> = emptyList(),
    val userModes : List<InfoWithModes> = emptyList()
)