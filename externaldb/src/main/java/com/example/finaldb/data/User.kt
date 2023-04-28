package com.example.finaldb.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name : String,
    val middleName : String,
    val lastName : String
) : Parcelable