package com.haman.dearme.data.db.entity

import androidx.compose.ui.graphics.Color
import com.haman.dearme.ui.model.MonthlyPlanByCategoryModel
import com.haman.dearme.ui.theme.ColorPalette

data class MonthlyPlanByCategoryEntity(
    val id: Long,
    val count: Int,
    val time: Long?,
    val categoryName: String?,
    val categoryColor: Long
)

fun MonthlyPlanByCategoryEntity.toModel() = MonthlyPlanByCategoryModel(
    count = count,
    value = time ?: 0L,
    categoryId = id,
    categoryName = categoryName ?: "Unknown",
    color = if (categoryColor == 0L) ColorPalette.DARK_GREY else Color(categoryColor)
)
