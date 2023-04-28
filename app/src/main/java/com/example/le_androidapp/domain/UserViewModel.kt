package com.example.le_androidapp.domain

import androidx.lifecycle.ViewModel
import com.example.finaldb.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {


}