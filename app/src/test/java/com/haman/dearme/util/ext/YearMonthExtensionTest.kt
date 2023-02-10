package com.haman.dearme.util.ext

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.YearMonth

internal class YearMonthExtensionTest {

    @Test
    fun getFullFormat_YearMonth_returnYYYYMM() {
        // 1. Given
        val now = YearMonth.of(2023, 2)

        // 2. When
        val result = now.fullFormat()

        // 3. Then
        assertThat(result, `is`("2023년 2월"))
    }
}