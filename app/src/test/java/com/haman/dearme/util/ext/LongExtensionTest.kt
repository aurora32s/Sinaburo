package com.haman.dearme.util.ext

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class LongExtensionTest {
    @Test
    fun getFullTimeFormat_Long_returnDateDuration() {
        // 1. Given
        val now = LocalDate.of(2023,2,10)
        val date = LocalDate.of(2023,2,15)

        // 2. When
        val diff = date.getTime() - now.getTime()
        val result = diff.fullTimeFormat()

        // 3. Then
        assertThat(result, `is`("5일"))
    }

    @Test
    fun getFullTimeFormat_Long_returnTimeDuration() {
        // 1. Given
        val now = LocalDateTime.of(
            2023, 2, 10, 20, 10, 35
        )
        val date = LocalDateTime.of(
            2023, 2, 11, 23, 59,59
        )

        // 2. When
        val diff = date.getTime() - now.getTime()
        val result = diff.fullTimeFormat()

        // 3. Then
        assertThat(result, `is`("1일 3시간 49분 24초"))
    }

    @Test
    fun getFullTimeFormat_Long_returnMonthDuration() {
        // 1. Given
        val now = LocalDate.of(2023,2,10)
        val date = LocalDate.of(2023,5,15)

        // 2. When
        val diff = date.getTime() - now.getTime()
        val result = diff.fullTimeFormat()

        // 3. Then
        assertThat(result, `is`("3개월 4일"))
    }

    @Test
    fun getFullTimeFormat_Long_returnYearDuration() {
        // 1. Given
        val now = LocalDate.of(2023,2,10)
        val date = LocalDate.of(2024,2,10)

        // 2. When
        val diff = date.getTime() - now.getTime()
        val result = diff.fullTimeFormat()

        // 3. Then
        assertThat(result, `is`("1년 5일"))
    }
}