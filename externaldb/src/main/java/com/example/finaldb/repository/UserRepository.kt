package com.example.finaldb.repository

import com.example.finaldb.entities.UserInfo
import com.example.finaldb.entities.relations.InfoAndReadyByDevice
import com.example.finaldb.entities.relations.InfoAndResult
import com.example.finaldb.entities.relations.InfoWithModes
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUserInfo(userInfo: UserInfo)

    suspend fun deleteUserInfo()

    fun getAllUsers() : Flow<List<UserInfo>>

    fun getUser(userInfo: UserInfo) : Flow<UserInfo>

    suspend fun getInfoAndResult() : List<InfoAndResult>

    suspend fun getInfoAndReadByDevice() : List<InfoAndReadyByDevice>

    suspend fun getInfoAndModes() : List<InfoWithModes>
}