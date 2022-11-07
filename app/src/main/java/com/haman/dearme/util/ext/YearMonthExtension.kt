package com.haman.dearme.util.ext

import java.time.YearMonth

fun YearMonth.fullFormat(): String {
    val year = this.year
    val month = this.month.value

    return "${year}년 ${month}월"
}