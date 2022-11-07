package com.haman.dearme.data.db.entity

import com.haman.dearme.ui.model.DiaryRateModel
import java.time.LocalDate

data class DiaryRateEntity(
    val id: Long,
    val content: String,
    val rate: Int,
    val year: Int,
    val month: Int,
    val day: Int
)

fun DiaryRateEntity.toModel() = DiaryRateModel(
    id = id,
    content = content,
    rate = rate,
    date = LocalDate.of(year, month, day)
)
