package com.haman.dearme.data.db.entity

import androidx.room.*
import androidx.room.ForeignKey.SET_NULL
import com.haman.dearme.ui.model.plan.PlanModel
import com.haman.dearme.ui.model.plan.PlanState

@Entity(
    tableName = "plan",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = SET_NULL
        )
    ]
)
data class PlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val title: String,
    val content: String,
    val startedAt: Long,
    val state: Int = PlanState.INIT.ordinal,
    val year: Int,
    val month: Int,
    val day: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Long?
)

fun PlanEntity.toModel() = PlanModel(
    id = id,
    title = title,
    content = content,
    startedAt = startedAt,
    state = PlanState.getPlanStateById(state),
    year = year,
    month = month,
    day = day,
    categoryId = categoryId
)