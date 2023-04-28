package com.example.finaldb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finaldb.entities.UserResult
import com.example.finaldb.entities.relations.ResultWithReadByDevices
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(userResult: UserResult)

    @Query("SELECT * FROM userresult")
    fun getAllResults() : Flow<List<UserResult>>

    @Query("SELECT * FROM userresult WHERE userId =:userId")
    fun getResult(userId : Int) : Flow<UserResult>

    @Query("SELECT * FROM userresult")
    suspend fun getResultWithReadByDevices() : List<ResultWithReadByDevices>
}