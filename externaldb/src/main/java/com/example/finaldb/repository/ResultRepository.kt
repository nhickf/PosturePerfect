package com.example.finaldb.repository

import com.example.finaldb.entities.UserInfo
import com.example.finaldb.entities.UserResult
import com.example.finaldb.entities.relations.ResultWithReadByDevices
import kotlinx.coroutines.flow.Flow

interface ResultRepository {

    suspend fun insertResult(userResult: UserResult)

    fun getAllResults() : Flow<List<UserResult>>

    fun getResult(userInfo: UserInfo) : Flow<UserResult>

    suspend fun getResultWithReadByDevices() : List<ResultWithReadByDevices>

}