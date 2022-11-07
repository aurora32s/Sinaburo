package com.haman.dearme.data.db.entity

import com.haman.dearme.ui.model.plan.PlanCountModel

data class MonthlyPlanEntity(
    val date: Int,
    val completedPlan: Int,
    val noneCompletedPlan: Int
)

fun MonthlyPlanEntity.toModel() = PlanCountModel(
    date = date,
    completedPlan = completedPlan,
    noneCompletedPlan = noneCompletedPlan
)