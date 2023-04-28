package com.example.finaldb.repository

import com.example.finaldb.entities.Modes
import com.example.finaldb.entities.UserInfo
import com.example.finaldb.entities.UserResult
import com.example.finaldb.entities.relations.ModesAndResults
import com.example.finaldb.entities.relations.ModesAndResultsRef
import com.example.finaldb.entities.relations.ModesWithReadByDevice
import kotlinx.coroutines.flow.Flow

interface ModeRepository {

    suspend fun insertMode(modes: Modes)

    fun getAllModes() : Flow<List<Modes>>

    fun getModesByUser(userInfo: UserInfo) : Flow<Modes>

    fun getModesByResult(result: UserResult) : Flow<List<Modes>>

    suspend fun insertReference(modesAndResultsRef: ModesAndResultsRef)

    suspend fun getModesAndResults() : List<ModesAndResults>

    suspend fun getModesAndReadByDevice() : List<ModesWithReadByDevice>

}