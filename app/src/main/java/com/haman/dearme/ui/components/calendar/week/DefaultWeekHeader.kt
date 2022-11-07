package com.haman.dearme.ui.components.calendar.week

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.haman.dearme.ui.components.text.Body2
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.theme.ColorPalette
import java.time.DayOfWeek
import java.time.format.TextStyle.SHORT
import java.util.*

@Composable
fun DefaultWeekHeader(
    dayOfWeek: List<DayOfWeek>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                else ColorPalette.White
            ),
    ) {
        dayOfWeek.forEach { date ->
            Sub1(
                text = date.getDisplayName(SHORT, Locale.getDefault()),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

internal fun <T> Array<T>.rotateRight(n: Int): List<T> = takeLast(n) + dropLast(n)