package com.haman.dearme.data.db.entity

import androidx.compose.ui.graphics.Color
import com.haman.dearme.ui.model.plan.PlanDiaryModel
import com.haman.dearme.ui.model.plan.PlanState
import com.haman.dearme.ui.theme.ColorPalette

data class PlanDiaryEntity(
    val id: Long,
    val title: String,
    val state: Int,
    val categoryName: String?,
    val categoryColor: Long,
    val duration: Long?,
    val year: Int,
    val month: Int,
    val day: Int
)

fun PlanDiaryEntity.toModel() = PlanDiaryModel(
    id = id,
    title = title,
    state = PlanState.getPlanStateById(state),
    categoryName = categoryName ?: "Unknown",
    color = if (categoryColor == 0L) ColorPalette.DARK_GREY else Color(categoryColor),
    value = duration ?: 0,
    year = year,
    month = month,
    day = day
)
