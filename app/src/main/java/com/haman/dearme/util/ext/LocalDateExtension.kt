package com.haman.dearme.util.ext

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

fun LocalDate.fullFormat(): String {
    val year = this.year
    val month = this.month.value
    val date = this.dayOfMonth
    val dateOfWeek = this.dayOfWeekText()

    return "${year}년 ${month}월 ${date}일 ${dateOfWeek}요일"
}

fun LocalDate.dayOfWeekText() = this.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
fun LocalDateTime.dayOfWeekText() =
    this.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

fun LocalDateTime.fullTimeFormat(): String {
    val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy.MM.dd a KK:mm", Locale.KOREA)
    return this.format(formatter)
}

fun LocalDateTime.dateFormat(): String {
    val month = this.month.value
    val date = this.dayOfMonth
    val dateOfWeek = this.dayOfWeekText()

    return "${month}월 ${date}일 ${dateOfWeek}요일"
}

fun LocalDateTime.timeFormat(): String {
    val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("a KK:mm", Locale.getDefault())
    return this.format(formatter)
}

fun LocalDate.getLastDate() = this.withDayOfMonth(lengthOfMonth()).dayOfMonth

fun LocalDate.getYearAndMonth(): String {
    val year = this.year
    val month = this.month.value

    return "$year/$month"
}

fun LocalDate.getTime() = this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
fun LocalDateTime.getTime() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()