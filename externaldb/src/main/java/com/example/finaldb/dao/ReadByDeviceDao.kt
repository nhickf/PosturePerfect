package com.example.finaldb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.finaldb.entities.ReadByDevice
import com.example.finaldb.entities.relations.ReadByDeviceWithResult
import com.example.finaldb.entities.relations.ReadByDeviceWithResultRef
import kotlinx.coroutines.flow.Flow

@Dao
interface ReadByDeviceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReadyByDevice(readByDevice: ReadByDevice)

    @Query("SELECT * FROM readbydevice where userId=:userId")
    fun getReadyByDevice(userId : Int) : Flow<ReadByDevice?>

    @Query("SELECT * FROM readbydevice")
    fun getAllReadyByDevice() : Flow<List<ReadByDevice>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(join: ReadByDeviceWithResultRef)

    @Transaction
    @Query("SELECT * FROM readbydevice")
    suspend fun getReadyByDeviceWithResults() : List<ReadByDeviceWithResult>
}