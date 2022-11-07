package com.haman.dearme.ui.components.calendar.week

import com.haman.dearme.ui.components.calendar.day.Day
import javax.annotation.concurrent.Immutable

@Immutable
internal class Week(
    val isFirstWeekOfTheMonth: Boolean = false,
    val days: List<Day>
)