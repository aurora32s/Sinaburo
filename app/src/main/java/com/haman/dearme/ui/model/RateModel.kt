package com.haman.dearme.ui.model

import com.haman.dearme.data.db.entity.RateEntity

data class RateModel(
    val id: Long?,
    val rate: Int,
    val year: Int,
    val month: Int,
    val day: Int
)

fun RateModel.toEntity() = RateEntity(
    id = id,
    rate = rate,
    year = year,
    month = month,
    day = day
)
