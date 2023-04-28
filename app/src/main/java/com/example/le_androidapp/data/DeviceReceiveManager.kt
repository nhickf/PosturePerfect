package com.example.le_androidapp.data;

import com.example.le_androidapp.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow

interface DeviceReceiveManager {

    val data: MutableSharedFlow<Resource<DeviceResult>>

    fun reconnect()

    fun disconnect()

    fun startReceiving()

    fun closeConnection()

}