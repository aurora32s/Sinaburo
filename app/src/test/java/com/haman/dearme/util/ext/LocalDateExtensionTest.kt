package com.haman.dearme.util.ext

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.LocalDate

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
}