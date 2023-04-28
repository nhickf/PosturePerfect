package com.example.finaldb.repository

import com.example.finaldb.dao.UserDao
import com.example.finaldb.entities.UserInfo
import com.example.finaldb.entities.relations.InfoAndReadyByDevice
import com.example.finaldb.entities.relations.InfoAndResult
import com.example.finaldb.entities.relations.InfoWithModes
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val getUserDao: UserDao) : UserRepository {

    val allUser: Flow<List<UserInfo>> = getUserDao.getAllNames()

    override suspend fun insertUserInfo(userInfo: UserInfo) {
        getUserDao.insertUser(userInfo)
    }

    override suspend fun deleteUserInfo() {
        getUserDao.deleteUser()
    }

    override fun getAllUsers(): Flow<List<UserInfo>> {
       return getUserDao.getAllNames()
    }

    override fun getUser(userInfo: UserInfo): Flow<UserInfo> {
        return getUserDao.getUser(userId = userInfo.userId)
    }

    override suspend fun getInfoAndResult(): List<InfoAndResult> {
        return getUserDao.getInfoAndResult()
    }

    override suspend fun getInfoAndReadByDevice(): List<InfoAndReadyByDevice> {
        return getUserDao.getInfoAndReadByDevice()
    }

    override suspend fun getInfoAndModes(): List<InfoWithModes> {
        return getUserDao.getInfoAndModes()
    }
}