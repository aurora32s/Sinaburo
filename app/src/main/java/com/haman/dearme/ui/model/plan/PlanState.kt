package com.haman.dearme.ui.model.plan

enum class PlanState {
    INIT,
    START,
    PAUSE,
    STOP;

    companion object {
        fun getPlanStateById(state: Int): PlanState =
            PlanState.values().find { it.ordinal == state } ?: INIT
    }
}