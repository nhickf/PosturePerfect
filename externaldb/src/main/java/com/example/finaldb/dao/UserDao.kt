package com.example.finaldb.dao

import androidx.room.*
import com.example.finaldb.entities.UserInfo
import com.example.finaldb.entities.relations.InfoAndReadyByDevice
import com.example.finaldb.entities.relations.InfoAndResult
import com.example.finaldb.entities.relations.InfoWithModes
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userInfo: UserInfo)

    @Query("DELETE FROM userinfo")
    suspend fun deleteUser()

    @Query("SELECT * FROM userinfo ORDER BY userId ASC")
    fun getAllNames(): Flow<List<UserInfo>>

    @Query("SELECT * FROM userinfo WHERE userId=:userId")
    fun getUser(userId : Int): Flow<UserInfo>

    @Transaction
    @Query("SELECT * FROM userinfo")
    suspend fun getInfoAndResult(): List<InfoAndResult>

    @Transaction
    @Query("SELECT * FROM userinfo")
    suspend fun getInfoAndReadByDevice() : List<InfoAndReadyByDevice>

    @Transaction
    @Query("SELECT * FROM userinfo")
    suspend fun getInfoAndModes() : List<InfoWithModes>




}