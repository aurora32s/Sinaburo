package com.haman.dearme.ui.model

import com.haman.dearme.data.db.entity.DiaryEntity

data class DiaryModel(
    val id: Long? = null,
    val content: String,
    val year: Int,
    val month: Int,
    val day: Int
)

fun DiaryModel.toEntity() = DiaryEntity(
    id = id,
    content = content,
    year = year,
    month = month,
    day = day
)