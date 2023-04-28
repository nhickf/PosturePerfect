package com.example.finaldb.repository

import com.example.finaldb.dao.ReadByDeviceDao
import com.example.finaldb.entities.ReadByDevice
import com.example.finaldb.entities.UserInfo
import com.example.finaldb.entities.relations.ReadByDeviceWithResult
import com.example.finaldb.entities.relations.ReadByDeviceWithResultRef
import kotlinx.coroutines.flow.Flow

class ReadByDeviceRepositoryImpl(private val readByDeviceDao: ReadByDeviceDao) : ReadByDeviceRepository {
    override suspend fun insertReadyByDevice(readByDevice: ReadByDevice) {
        readByDeviceDao.insertReadyByDevice(readByDevice)
    }

    override fun getReadyByDevice(userInfo: UserInfo): Flow<ReadByDevice?> {
        return readByDeviceDao.getReadyByDevice(userInfo.userId)
    }

    override fun getAllReadyByDevice(): Flow<List<ReadByDevice>> {
        return readByDeviceDao.getAllReadyByDevice()
    }

    override suspend fun insertReference(readByDeviceWithResultRef: ReadByDeviceWithResultRef) {
        readByDeviceDao.insert(readByDeviceWithResultRef)
    }

    override suspend fun getReadyByDeviceWithResults(): List<ReadByDeviceWithResult> {
        return readByDeviceDao.getReadyByDeviceWithResults()
    }
}