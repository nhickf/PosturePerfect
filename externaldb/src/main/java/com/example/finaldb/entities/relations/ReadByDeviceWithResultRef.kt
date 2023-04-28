package com.example.finaldb.entities.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.example.finaldb.entities.ReadByDevice
import com.example.finaldb.entities.UserResult
import kotlinx.coroutines.flow.Flow

data class ReadByDeviceWithResult(
    @Embedded val readByDevice: ReadByDevice,
    @Relation(
        parentColumn = "rdbId",
        entityColumn = "uresultId",
        associateBy = Junction(ReadByDeviceWithResultRef::class)
    )
    val results: List<UserResult>?
)

data class ResultWithReadByDevices(
    @Embedded val userResult: UserResult,
    @Relation(
        parentColumn = "uresultId",
        entityColumn = "rdbId",
        associateBy = Junction(ReadByDeviceWithResultRef::class)
    )
    val results: List<ReadByDevice>?
)

@Entity(primaryKeys = ["rdbId","uresultId"])
data class ReadByDeviceWithResultRef(
    val rdbId : Int,
    val uresultId : Int,
)
