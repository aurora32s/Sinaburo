package com.haman.dearme.ui.model.plan

import com.haman.dearme.data.db.entity.PlanEntity

data class PlanModel(
    val id: Long? = null,
    val title: String,
    val content: String,
    val startedAt: Long,
    val state: PlanState,
    val year: Int,
    val month: Int,
    val day: Int,
    val categoryId: Long?
)

fun PlanModel.toEntity() = PlanEntity(
    id = id,
    title = title,
    content = content,
    startedAt = startedAt,
    state = state.ordinal,
    year = year,
    month = month,
    day = day,
    categoryId = categoryId
)
