package com.haman.dearme.ui.components.calendar.day

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.theme.ColorPalette
import java.time.LocalDate

@Composable
fun BaseDate(
    currentDate: LocalDate,
    state: DayState,
    currentDayColor: Color = ColorPalette.GREY,
    onClickDate: (LocalDate) -> Unit
) {
    val date = state.date
    val isCurrentDay = currentDate == date

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .heightIn(60.dp)
            .clickable { onClickDate(date) },
        border = BorderStroke(
            color = ColorPalette.ACCENT.copy(alpha = if (state.isFromCurrentMonth) 1f else 0.3f),
            width = 1.dp
        ),
        backgroundColor = when {
            isCurrentDay -> ColorPalette.LIGHT_ACCENT.copy(alpha = if (isSystemInDarkTheme()) 0.3f else 1f)
            isSystemInDarkTheme() -> ColorPalette.DARK_BACKGROUND
            else -> ColorPalette.White
        }
    ) {
        Sub2(
            text = date.dayOfMonth.toString(),
            color = (if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND).copy(
                alpha = if (state.isFromCurrentMonth) 1f else 0.3f
            )
        )
    }
}