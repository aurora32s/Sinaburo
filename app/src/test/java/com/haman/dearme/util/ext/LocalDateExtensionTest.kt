package com.haman.dearme.util.ext

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

internal class LocalDateExtensionTest {
    @Test
    fun getFullFormat_LocalDate_returnYYYYMMddDate() {
        // 1. Given
        val date = LocalDate.of(2023, 2, 3)

        // 2. When
        val result = date.fullFormat()

        // 3. Then
        assertThat(result, `is`("2023년 2월 3일 금요일"))
    }

    @Test
    fun getDayOfWeekText_LocalDate_returnLocalDefaultDate() {
        // 1. Given
        val date = LocalDate.of(2023, 2, 3)
        // 2. When
        val result = date.dayOfWeekText()
        // 3. Then
        assertThat(result, `is`("금"))
    }

    @Test
    fun getDayOfWeekText_LocalDateTime_returnLocaleDefaultDate() {
        // 1. Given
        val time = LocalDateTime.of(
            2023, 2, 3, 12, 59
        )
        // 2. When
        val result = time.dayOfWeekText()
        // 3. Then
        assertThat(result, `is`("금"))
    }

    @Test
    fun getFullTimeFormat_LocalDateTime_returnYYYYMMDDaHHmm() {
        // 1. Given
        val time = LocalDateTime.of(
            2023, 2, 3, 12, 59
        )
        // 2. When
        val result = time.fullTimeFormat()
        // 3. Then
        assertThat(result, `is`("2023.02.03 오후 12:59"))
    }

    @Test
    fun getDateFormat_LocalDateTime_returnYYYYDDDate() {
        // 1. Given
        val time = LocalDateTime.of(
            2023, 2, 3, 12, 59
        )
        // 2. When
        val result = time.dateFormat()
        // 3. Then
        assertThat(result, `is`("2월 3일 금요일"))
    }

    @Test
    fun getTimeFormat_LocalDateTime_returnHHmm() {
        // 1. Given
        val time = LocalDateTime.of(
            2023, 2, 3, 12, 59
        )
        // 2. When
        val result = time.timeFormat()
        // 3. Then
        assertThat(result, `is`("오후 12:59"))
    }

    @Test
    fun getLastDate_LocalDate_returnLastDate() {
        // 1. Given
        val date = LocalDate.of(2023,1,3)
        // 2. When
        val result = date.getLastDate()
        // 3. Then
        assertThat(result, `is`(31))
    }

    @Test
    fun getYearAndMonth_LocalDate_returnYYYYMM() {
        // 1. Given
        val date = LocalDate.of(2023,1,3)
        // 2. When
        val result = date.getYearAndMonth()
        // 3. Then
        assertThat(result, `is`("2023/01"))
    }
}