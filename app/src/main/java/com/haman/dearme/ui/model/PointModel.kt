package com.haman.dearme.ui.model

import com.haman.dearme.data.db.entity.BadPointEntity
import com.haman.dearme.data.db.entity.GoodPointEntity

data class PointModel(
    val id: Long? = null,
    val content: String,
    val year: Int,
    val month: Int,
    val day: Int
)

fun PointModel.toGoodEntity() = GoodPointEntity(
    id = id,
    content = content,
    year = year,
    month = month,
    day = day
)

fun PointModel.toBadEntity() = BadPointEntity(
    id = id,
    content = content,
    year = year,
    month = month,
    day = day
)
