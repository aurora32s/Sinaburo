package com.haman.dearme.data.db.entity

import androidx.compose.ui.graphics.Color
import com.haman.dearme.ui.model.plan.PlanMainModel
import com.haman.dearme.ui.model.plan.PlanState
import com.haman.dearme.ui.theme.ColorPalette

data class PlanMainEntity(
    val id: Long,
    val title: String,
    val state: Int,
    val categoryName: String?,
    val categoryColor: Long
)

fun PlanMainEntity.toModel() = PlanMainModel(
    id = id,
    title = title,
    state = PlanState.getPlanStateById(state),
    categoryName = categoryName ?: "Unknown",
    categoryColor = if (categoryColor == 0L) ColorPalette.DARK_GREY else Color(categoryColor)
)
