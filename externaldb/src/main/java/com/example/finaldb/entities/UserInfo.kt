package com.example.finaldb.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import com.example.finaldb.R
import com.example.finaldb.data.Gender

@Entity
data class UserInfo (
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,

    @ColumnInfo (name = "First Name")
    val firstName: String,

    @ColumnInfo (name = "Last Name")
    val lastName: String,

    @ColumnInfo (name = "Middle Name")
    val middleName: String,

    val userAge : Int,

    val userGender : Gender,

    val contactNumber : String,

    val userCity : String,

    val userEmail : String,

    var imageId: Int = R.drawable.profile,

    val isLogin : Boolean = false,
)
