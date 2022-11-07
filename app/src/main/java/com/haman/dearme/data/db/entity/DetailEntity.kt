package com.haman.dearme.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.haman.dearme.ui.model.DetailModel

@Entity(
    tableName = "detail",
    foreignKeys = [
        ForeignKey(
            entity = PlanEntity::class,
            parentColumns = ["id"],
            childColumns = ["plan_id"],
            onDelete = CASCADE
        )
    ]
)
data class DetailEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "plan_id")
    val planId: Long,
    val title: String,
    val completed: Boolean
)

fun DetailEntity.toModel() = DetailModel(
    id = id,
    planId = planId,
    title = title,
    completed = completed
)
