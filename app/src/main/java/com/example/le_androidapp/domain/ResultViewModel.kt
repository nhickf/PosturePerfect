package com.example.le_androidapp.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaldb.repository.ReadByDeviceRepository
import com.example.finaldb.repository.ResultRepository
import com.example.le_androidapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val readByDeviceRepository: ReadByDeviceRepository
) : ViewModel() {

    val result = readByDeviceRepository.getReadyByDevice(
        Constants.MOCK_USER
    ).map { readByDevice ->
        Log.e("ResultViewModel", "$readByDevice")
        readByDevice?.let {
            ResultViewModelState(
                pitchNumber = readByDevice.calibratedPitch,
                rollNumber = readByDevice.calibratedRoll,
                flexNumber = readByDevice.calibratedFlexSensor
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        null
    )

    data class ResultViewModelState(
        val pitchNumber : Float,
        val rollNumber : Float,
        val flexNumber : Float
    )
}