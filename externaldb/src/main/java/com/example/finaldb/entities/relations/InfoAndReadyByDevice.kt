package com.example.finaldb.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.finaldb.entities.ReadByDevice
import com.example.finaldb.entities.UserInfo

data class InfoAndReadyByDevice(
    @Embedded val userInfo: UserInfo,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val readByDevice: ReadByDevice?
)
