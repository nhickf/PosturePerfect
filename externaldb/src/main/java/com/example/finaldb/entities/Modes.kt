package com.example.finaldb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Modes(
    @PrimaryKey(autoGenerate = false)
    val modeId: Int = 0,
    val workingMode: Boolean,
    val restingMode: Boolean,
    val resultId : Int,
    val userId : Int,
)