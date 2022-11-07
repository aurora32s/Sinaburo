package com.haman.dearme.ui.model

data class HistoryModel(
    val completedPlanCnt: Int,
    val noneCompletedPlanCnt: Int,
    val title: String,
    val year: Int,
    val month: Int,
    val day: Int
)
