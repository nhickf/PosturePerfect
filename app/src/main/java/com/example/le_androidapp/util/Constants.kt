package com.example.le_androidapp.util

import com.example.finaldb.data.Gender
import com.example.finaldb.entities.UserInfo

object Constants {

    val MOCK_USER = UserInfo(
        userId = 1,
        firstName = "User",
        middleName = "One",
        lastName = "Name",
        userAge = 20,
        userGender = Gender.MALE,
        contactNumber = "09563897213",
        userCity = "Manila",
        userEmail = "test@gmail.com",
    )

}