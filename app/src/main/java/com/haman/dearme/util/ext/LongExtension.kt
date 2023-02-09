package com.haman.dearme.util.ext

import java.time.Instant
import java.time.ZoneId

fun Long.fullTimeFormat(): String {
    val result = mutableListOf<String>()
    val seconds = this / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val months = days / 30
    val years = months / 12

    if (years > 0) result.add("${years}년")
    if (months - years * 12 > 0) result.add("${months - years * 12}개월")
    if (days - months * 30 > 0) result.add("${days - months * 30}일")
    if (hours - days * 24 > 0) result.add("${hours - days * 24}시간")
    if (minutes - hours * 60 > 0) result.add("${minutes - hours * 60}분")
    if (seconds - minutes * 60 > 0) result.add("${seconds - minutes * 60}초")

    return result.joinToString(separator = " ")
}

fun Long.toLocalDateTime() =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()

fun Long.toLocalDate() = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
fun Long.formatToDate() = this.toLocalDate().fullFormat()