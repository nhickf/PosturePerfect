package com.example.finaldb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.finaldb.entities.Modes
import com.example.finaldb.entities.relations.ModesAndResults
import com.example.finaldb.entities.relations.ModesAndResultsRef
import com.example.finaldb.entities.relations.ModesWithReadByDevice
import kotlinx.coroutines.flow.Flow

@Dao
interface ModesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMode(userResult: Modes)

    @Query("SELECT * FROM modes")
    fun getAllModes() : Flow<List<Modes>>

    @Query("SELECT * FROM modes WHERE userId =:userId")
    fun getModesByUser(userId : Int) : Flow<Modes>

    @Query("SELECT * FROM modes WHERE resultId=:resultId")
    fun getModesByResult(resultId : Int):  Flow<List<Modes>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(join: ModesAndResultsRef)

    @Transaction
    @Query("SELECT * FROM modes")
    suspend fun getResultWithModes(): List<ModesAndResults>

    @Query("SELECT * FROM modes")
    suspend fun getModesAndReadByDevice() : List<ModesWithReadByDevice>



}