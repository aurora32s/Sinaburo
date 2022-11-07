package com.haman.dearme.ui.model

import com.haman.dearme.data.db.entity.GoodPointEntity

data class GoodPointModel(
    val id: Long? = null,
    val content: String,
    val year: Int,
    val month: Int,
    val day: Int
)

fun GoodPointModel.toEntity() = GoodPointEntity(
    id = id,
    content = content,
    year = year,
    month = month,
    day = day
)
