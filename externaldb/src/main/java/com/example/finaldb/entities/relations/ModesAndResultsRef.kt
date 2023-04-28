package com.example.finaldb.entities.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.example.finaldb.entities.Modes
import com.example.finaldb.entities.UserResult


data class ModesAndResults(
    @Embedded val modes : Modes,
    @Relation(
        parentColumn = "modeId",
        entityColumn = "uresultId",
        associateBy = Junction(ModesAndResultsRef::class)
    )
    val results : List<UserResult>?
)

@Entity(primaryKeys = ["modeId","uresultId"])
data class ModesAndResultsRef(
    val modeId : Int,
    val uresultId: Int,
)
