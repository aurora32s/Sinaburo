package com.haman.dearme.ui.model

import com.haman.dearme.data.db.entity.BadPointEntity

data class BadPointModel(
    val id: Long? = null,
    val content: String,
    val year: Int,
    val month: Int,
    val day: Int
)

fun BadPointModel.toEntity() = BadPointEntity(
    id = id,
    content = content,
    year = year,
    month = month,
    day = day
)
