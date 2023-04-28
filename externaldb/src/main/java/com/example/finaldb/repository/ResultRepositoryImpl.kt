package com.example.finaldb.repository

import com.example.finaldb.dao.ResultDao
import com.example.finaldb.entities.UserInfo
import com.example.finaldb.entities.UserResult
import com.example.finaldb.entities.relations.ResultWithReadByDevices
import kotlinx.coroutines.flow.Flow

class ResultRepositoryImpl(private val resultDao: ResultDao) : ResultRepository {
    override suspend fun insertResult(userResult: UserResult) {
        resultDao.insertResult(userResult)
    }

    override fun getAllResults(): Flow<List<UserResult>> {
        return resultDao.getAllResults()
    }

    override fun getResult(userInfo: UserInfo): Flow<UserResult> {
        return resultDao.getResult(userId = userInfo.userId)
    }

    override suspend fun getResultWithReadByDevices(): List<ResultWithReadByDevices> {
        return resultDao.getResultWithReadByDevices()
    }
}