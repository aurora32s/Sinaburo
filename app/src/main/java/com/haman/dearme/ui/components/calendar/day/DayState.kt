package com.haman.dearme.ui.components.calendar.day

import androidx.compose.runtime.Stable

@Stable
data class DayState(
    private val day: Day
) : Day by day
