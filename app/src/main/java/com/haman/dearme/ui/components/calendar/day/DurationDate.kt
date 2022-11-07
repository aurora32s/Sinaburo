package com.haman.dearme.ui.components.calendar.day

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.theme.ColorPalette
import java.time.LocalDate

@Composable
fun DurationDate(
    today: LocalDate,
    isModifyMode: Boolean,
    doneDate: List<LocalDate>,
    startedAt: LocalDate?,
    endedAt: LocalDate?,
    state: DayState,
    durationColor: Color = ColorPalette.GREY,
    onSelectDate: (LocalDate) -> Unit
) {
    val date = state.date
    val isInDuration = startedAt != null && endedAt != null && date in startedAt..endedAt
    val isDoneDate = date in doneDate

    val stroke = with(LocalDensity.current) { Stroke(2.dp.toPx()) }

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .heightIn(60.dp)
            .clickable { if (isModifyMode) onSelectDate(date) },
        border = BorderStroke(
            color = ColorPalette.ACCENT.copy(alpha = if (state.isFromCurrentMonth) 1f else 0.3f),
            width = 1.dp
        ),
        backgroundColor = when {
            state.isCurrentDay -> ColorPalette.LIGHT_ACCENT.copy(alpha = if (isSystemInDarkTheme()) 0.3f else 1f)
            isInDuration -> ColorPalette.LIGHT_PRIMARY.copy(alpha = if (isSystemInDarkTheme()) 0.3f else 1f)
            isSystemInDarkTheme() -> ColorPalette.DARK_BACKGROUND
            else -> ColorPalette.White
        }
    ) {
        Box(
            modifier = Modifier.padding(4.dp)
        ) {
            if (isModifyMode.not() && isInDuration && date <= today) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    if (isDoneDate) {
                        drawArc(
                            color = ColorPalette.Accent,
                            startAngle = -90f,
                            sweepAngle = 360f,
                            topLeft = Offset(0f, 0f),
                            size = Size(size.width, size.height),
                            useCenter = false,
                            style = stroke
                        )
                    } else if (isInDuration) {
                        drawLine(
                            color = ColorPalette.Accent,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, size.height),
                            strokeWidth = 4f
                        )
                        drawLine(
                            color = ColorPalette.Accent,
                            start = Offset(size.width, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = 4f
                        )
                    }
                }
            }
            Sub2(
                text = date.dayOfMonth.toString(),
                color = (if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND).copy(
                    alpha = if (state.isFromCurrentMonth) 1f else 0.3f
                )
            )
        }
    }
}