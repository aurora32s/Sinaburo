package com.haman.dearme.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.haman.dearme.ui.model.TimeModel

@Entity(
    tableName = "time",
    foreignKeys = [
        ForeignKey(
            entity = PlanEntity::class,
            parentColumns = ["id"],
            childColumns = ["plan_id"],
            onDelete = CASCADE
        )
    ]
)
data class TimeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(name = "plan_id")
    val planId: Long,
    val startedAt: Long,
    val endedAt: Long
)

fun TimeEntity.toModel() = TimeModel(
    id = id,
    planId = planId,
    startedAt = startedAt,
    endedAt = endedAt
)
