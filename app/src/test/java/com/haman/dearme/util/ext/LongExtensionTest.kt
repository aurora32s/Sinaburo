package com.haman.dearme.util.ext

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.LocalDate

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
}