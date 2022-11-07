package com.haman.dearme.ui.model

import com.haman.dearme.data.db.entity.DetailEntity

data class DetailModel(
    val id: Long? = null,
    val planId: Long? = null,
    val title: String,
    val completed: Boolean = false
)

fun DetailModel.toEntity(planId: Long) = DetailEntity(
    id = id,
    planId = planId,
    title = title,
    completed = completed
)
