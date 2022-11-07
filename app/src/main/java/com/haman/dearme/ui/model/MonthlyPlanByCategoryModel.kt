package com.haman.dearme.ui.model

import androidx.compose.ui.graphics.Color

data class MonthlyPlanByCategoryModel(
    val count: Int,
    val categoryId: Long,
    override val value: Long,
    val categoryName: String,
    override val color: Color
): BaseCircleGraphModel
