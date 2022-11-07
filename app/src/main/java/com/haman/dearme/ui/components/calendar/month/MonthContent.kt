package com.haman.dearme.ui.components.calendar.month

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.calendar.day.DayState
import com.haman.dearme.ui.components.calendar.week.WeekContent
import com.haman.dearme.ui.components.calendar.week.getWeeks
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

internal const val DaysOfWeek = 7

@Composable
fun MonthContent(
    showAdjacentMonths: Boolean,
    currentMonth: YearMonth,
    daysOfWeek: List<DayOfWeek>,
    today: LocalDate,
    modifier: Modifier = Modifier,
    dayContent: @Composable BoxScope.(DayState) -> Unit,
    weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            content = { weekHeader(daysOfWeek) })
        monthContainer { paddingValues ->
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(paddingValues)
            ) {
                currentMonth.getWeeks(
                    includeAdjacentMonths = showAdjacentMonths,
                    firstDayOfTheWeek = daysOfWeek.first(),
                    today = today
                ).forEach { week ->
                    WeekContent(
                        week = week,
                        dayContent = dayContent
                    )
                }
            }
        }
    }
}