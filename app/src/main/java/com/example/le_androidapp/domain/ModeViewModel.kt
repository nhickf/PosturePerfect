package com.example.le_androidapp.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaldb.data.Mode
import com.example.finaldb.data.toModesEntity
import com.example.finaldb.data.toUserEntity
import com.example.finaldb.repository.ModeRepository
import com.example.le_androidapp.util.Constants.MOCK_USER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModeViewModel @Inject constructor(
    private val modeRepository: ModeRepository
) : ViewModel() {

    val modeState = modeRepository.getModesByUser(MOCK_USER)
        .map { modes ->
            ModeViewModeState(
                restingMode = modes.restingMode,
                workingMode = modes.workingMode
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ModeViewModeState()
        )

    fun updateModes(isWorkingMode : Boolean){
        viewModelScope.launch {
            modeRepository.insertMode(Mode(
               userId = MOCK_USER.userId,
               restingMode = !isWorkingMode,
               workingMode = isWorkingMode,
               resultId = 0
            ).toModesEntity())
        }
    }

    data class ModeViewModeState(
        val workingMode : Boolean = false,
        val restingMode : Boolean = false,
    )

}