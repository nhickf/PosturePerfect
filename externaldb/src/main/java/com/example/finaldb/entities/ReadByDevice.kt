package com.example.finaldb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class ReadByDevice(
    @PrimaryKey(autoGenerate = false)
    val rdbId: Int = 0,
    val userId: Int,
    val modeId: Int,
    val badPostureCount: Int = 0,
    val calibratedRoll: Float,
    val calibratedPitch: Float,
    val calibratedFlexSensor: Float,
    val recordedTime: String,
    val mode: Boolean
)