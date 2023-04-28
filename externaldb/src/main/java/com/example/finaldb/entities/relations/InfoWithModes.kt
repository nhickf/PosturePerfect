package com.example.finaldb.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.finaldb.entities.Modes
import com.example.finaldb.entities.UserInfo

data class InfoWithModes (
    @Embedded val user: UserInfo,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val modes: List<Modes>?
)