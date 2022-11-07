package com.haman.dearme.ui.model.plan

import androidx.compose.ui.graphics.Color
import com.haman.dearme.ui.model.BaseCircleGraphModel

data class PlanDiaryModel(
    override val id: Long,
    override val title: String,
    override val state: PlanState,
    override val categoryName: String,
    val year: Int,
    val month: Int,
    val day: Int,
    override val color: Color,
    override val value: Long
) : BaseCircleGraphModel, PlanMainModel(id, title, state, categoryName, color)
