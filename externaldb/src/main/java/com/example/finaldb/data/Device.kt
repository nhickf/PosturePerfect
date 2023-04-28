package com.example.finaldb.data

import java.time.LocalDateTime

data class Device(
    val userId: Int,
    val modeId: Int,
    val badPostureCount: Int,
    val calibratedRoll: Float,
    val calibratedPitch: Float,
    val calibratedFlexSensor: Float,
    val recordedTime: LocalDateTime,
    val mode: Boolean
)
