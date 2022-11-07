package com.haman.dearme.ui.model.plan

import androidx.compose.ui.graphics.Color

open class PlanMainModel(
    open val id: Long,
    open val title: String,
    open val state: PlanState,
    open val categoryName: String,
    open val categoryColor: Color
)