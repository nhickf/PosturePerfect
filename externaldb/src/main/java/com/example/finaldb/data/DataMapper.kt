package com.example.finaldb.data

import com.example.finaldb.entities.Modes
import com.example.finaldb.entities.ReadByDevice
import com.example.finaldb.entities.UserInfo
import com.example.finaldb.entities.UserResult


fun User.toUserEntity() : UserInfo =
    UserInfo(
        firstName = name,
        lastName = lastName,
        middleName = middleName,
        userAge = 20,
        userGender = Gender.MALE,
        contactNumber = "09563897213",
        userCity = "Manila",
        userEmail = "test@gmail.com",
    )

fun Result.toUserResultEntity() : UserResult =
    UserResult(
        userId = userId,
        totalBadPostureCount = totalBadPostureCount,
        totalTime = totalTime,
        calibratedX = calibratedX,
        calibratedY = calibratedY,
        calibratedZ = calibratedZ,
        mode = mode
    )

fun Device.toReadByDeviceEntity() : ReadByDevice =
    ReadByDevice(
        rdbId = userId,
        userId = userId,
        modeId = modeId,
        mode = mode,
        badPostureCount = badPostureCount,
        calibratedFlexSensor = calibratedFlexSensor,
        calibratedPitch = calibratedPitch,
        calibratedRoll = calibratedRoll,
        recordedTime = recordedTime.toString(),
    )

fun Mode.toModesEntity() : Modes =
    Modes(
       modeId = userId,
       workingMode = workingMode,
       restingMode = restingMode,
       resultId = resultId,
       userId = userId
    )