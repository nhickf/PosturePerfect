package com.example.finaldb.repository

import com.example.finaldb.dao.ModesDao
import com.example.finaldb.entities.Modes
import com.example.finaldb.entities.UserInfo
import com.example.finaldb.entities.UserResult
import com.example.finaldb.entities.relations.ModesAndResults
import com.example.finaldb.entities.relations.ModesAndResultsRef
import com.example.finaldb.entities.relations.ModesWithReadByDevice
import kotlinx.coroutines.flow.Flow

class ModeRepositoryImpl(private val modeDao : ModesDao) : ModeRepository {

    override suspend fun insertMode(modes: Modes) {
        modeDao.insertMode(userResult = modes)
    }

    override fun getAllModes(): Flow<List<Modes>> {
        return modeDao.getAllModes()
    }

    override fun getModesByUser(userInfo: UserInfo): Flow<Modes> {
        return modeDao.getModesByUser(userId = userInfo.userId)
    }

    override fun getModesByResult(result: UserResult): Flow<List<Modes>> {
        return modeDao.getModesByResult(resultId = result.uresultId)
    }

    override suspend fun insertReference(modesAndResultsRef: ModesAndResultsRef) {
        modeDao.insert(modesAndResultsRef)
    }

    override suspend fun getModesAndResults(): List<ModesAndResults> {
        return modeDao.getResultWithModes()
    }

    override suspend fun getModesAndReadByDevice(): List<ModesWithReadByDevice> {
        return modeDao.getModesAndReadByDevice()
    }
}