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
        DateTimeFormatter.ofPattern("yyyy.MM.dd a hh:mm", Locale.getDefault())
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
        DateTimeFormatter.ofPattern("a hh:mm", Locale.getDefault())
    return this.format(formatter)
}

/**
 * 해당 월의 마지막 날짜 반환
 * ex. 1월 -> 31
 */
fun LocalDate.getLastDate() = this.withDayOfMonth(lengthOfMonth()).dayOfMonth

fun LocalDate.getYearAndMonth(): String {
    val year = this.year
    val month = this.month.value

    return "$year/${"%02d".format(month)}"
}

fun LocalDate.getTime() = this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
fun LocalDateTime.getTime() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()