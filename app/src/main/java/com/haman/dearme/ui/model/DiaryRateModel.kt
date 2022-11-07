package com.haman.dearme.ui.model

import java.time.LocalDate

data class DiaryRateModel(
    val id: Long,
    val content: String,
    val rate: Int,
    val date: LocalDate
)
