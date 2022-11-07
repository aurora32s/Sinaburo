package com.haman.dearme.ui.components.calendar.day

import java.time.DayOfWeek
import java.time.LocalDate

val NOW = LocalDate.now()

internal infix fun DayOfWeek.daysUntil(other: DayOfWeek) = (7 + (value - other.value)) % 7