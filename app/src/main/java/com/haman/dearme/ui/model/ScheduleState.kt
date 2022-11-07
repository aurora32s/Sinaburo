package com.haman.dearme.ui.model

enum class ScheduleState {
    TODO,
    SUCCESS,
    FAIL;

    companion object {
        fun getScheduleState(id: Int): ScheduleState = values().find { it.ordinal == id } ?: TODO
    }
}