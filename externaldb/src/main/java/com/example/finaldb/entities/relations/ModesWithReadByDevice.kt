package com.example.finaldb.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.finaldb.entities.Modes
import com.example.finaldb.entities.ReadByDevice

data class ModesWithReadByDevice(
    @Embedded val modes : Modes,
    @Relation(
        parentColumn = "modeId",
        entityColumn = "modeId"
    )
    val devices : List<ReadByDevice>?
)
