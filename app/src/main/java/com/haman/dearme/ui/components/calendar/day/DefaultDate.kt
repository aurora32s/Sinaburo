package com.haman.dearme.ui.components.calendar.day

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.model.ScheduleModel
import com.haman.dearme.ui.model.plan.PlanCountModel
import com.haman.dearme.ui.theme.ColorPalette
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun DefaultDate(
    planCount: Map<Int, PlanCountModel>,
    state: DayState,
    selectedDate: LocalDate,
    schedules: List<ScheduleModel>,
    currentDayColor: Color = ColorPalette.SPACER,
    onClickDate: (LocalDate) -> Unit
) {
    val date = state.date
    val count = planCount[state.date.dayOfMonth]

    val startedAt = LocalDateTime.of(date, LocalTime.of(0, 0))
    val endedAt = LocalDateTime.of(date, LocalTime.of(23, 59))
    val isSchedule = schedules.any { it.startedAt <= endedAt && it.endedAt >= startedAt }

    Box(
        modifier = Modifier
            .height(60.dp)
            .padding((0.5).dp)
            .border(
                color = ColorPalette.ACCENT.copy(alpha = if (state.isFromCurrentMonth) 1f else 0.3f),
                width = 1.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .clip(RoundedCornerShape(5.dp))
            .background(
                color = when {
                    state.isCurrentDay -> ColorPalette.LIGHT_ACCENT.copy(alpha = if (isSystemInDarkTheme()) 0.3f else 1f)
                    date == selectedDate -> ColorPalette.LIGHT_PRIMARY.copy(alpha = if (isSystemInDarkTheme()) 0.3f else 1f)
                    isSystemInDarkTheme() -> ColorPalette.DARK_BACKGROUND
                    else -> ColorPalette.White
                }
            )
            .clickable { onClickDate(date) }
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSchedule) {
                    Box(modifier = Modifier
                        .size(6.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(
                            if (isSystemInDarkTheme()) ColorPalette.White
                            else ColorPalette.DARK_PRIMARY
                        )
                    )
                }
                Sub2(
                    text = date.dayOfMonth.toString(),
                    color = (if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND).copy(
                        alpha = if (state.isFromCurrentMonth) 1f else 0.3f
                    )
                )
            }
            if (state.isFromCurrentMonth && count != null) {
                if (count.completedPlan > 0)
                    PlanCountItem(
                        color = ColorPalette.ACCENT,
                        count = count.completedPlan
                    )
                if (count.noneCompletedPlan > 0)
                    PlanCountItem(
                        color = ColorPalette.SECOND,
                        count = count.noneCompletedPlan
                    )
            }
        }
    }
}

@Composable
fun PlanCountItem(
    color: Color,
    count: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Sub2(
            text = count.toString()
        )
    }
}