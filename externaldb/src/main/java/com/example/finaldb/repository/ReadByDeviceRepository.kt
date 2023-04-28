package com.example.finaldb.repository

import com.example.finaldb.entities.ReadByDevice
import com.example.finaldb.entities.UserInfo
import com.example.finaldb.entities.relations.ReadByDeviceWithResult
import com.example.finaldb.entities.relations.ReadByDeviceWithResultRef
import kotlinx.coroutines.flow.Flow

interface ReadByDeviceRepository {

    suspend fun insertReadyByDevice(readByDevice: ReadByDevice)

    fun getReadyByDevice(userInfo: UserInfo) : Flow<ReadByDevice?>

    fun getAllReadyByDevice() : Flow<List<ReadByDevice>>

    suspend fun insertReference(readByDeviceWithResultRef: ReadByDeviceWithResultRef)

    suspend fun getReadyByDeviceWithResults() : List<ReadByDeviceWithResult>

}