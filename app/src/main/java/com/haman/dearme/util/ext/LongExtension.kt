package com.haman.dearme.util.ext

import java.time.Instant
import java.time.ZoneId

fun Long.fullTimeFormat(): String {
    val seconds = this / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val months = days / 30
    val years = months / 12

    var result = StringBuilder()
    if (years > 0) result.append("${years}년 ")
    if (months > 0) result.append("${months - years * 12}개월 ")
    if (days > 0) result.append("${days - months * 30}일 ")
    if (hours > 0) result.append("${hours - days * 24}시간 ")
    if (minutes > 0) result.append("${minutes - hours * 60}분 ")
    if (seconds > 0) result.append("${seconds - minutes * 60}초")

    return result.toString()
}

fun Long.toLocalDateTime() =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()

fun Long.toLocalDate() = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
fun Long.formatToDate() = this.toLocalDate().fullFormat()