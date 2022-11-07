package com.haman.dearme.ui.model

import java.time.LocalDate

data class DiaryChallengeModel(
    val id: Long,
    val title: String,
    val image: String,
    val startedAt: LocalDate,
    val endedAt: LocalDate,
    val categoryName: String,
    val categoryColor: Long
)
