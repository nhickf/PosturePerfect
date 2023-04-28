package com.example.finaldb.data

data class Result(
    val totalBadPostureCount: Int,
    val totalTime: Long = 0,
    val calibratedX: Float,
    val calibratedY: Float,
    val calibratedZ: Float,
    val userId: Int,
    val mode: Boolean
)