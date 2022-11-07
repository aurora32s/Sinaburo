package com.haman.dearme.ui.screen.montly

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haman.dearme.ui.components.calendar.CalendarContainer
import com.haman.dearme.ui.components.calendar.CalendarState
import com.haman.dearme.ui.components.calendar.day.DefaultDate
import com.haman.dearme.ui.components.calendar.rememberCalendarState
import com.haman.dearme.ui.components.common.footer
import com.haman.dearme.ui.components.container.YearMonthAppBarContainer
import com.haman.dearme.ui.components.graph.CircleGraphWithTitle
import com.haman.dearme.ui.components.graph.EvenListGraph
import com.haman.dearme.ui.components.graph.EvenSingleBarGraph
import com.haman.dearme.ui.components.header.BaseAppBar
import com.haman.dearme.ui.components.input.SavableInput
import com.haman.dearme.ui.components.item.*
import com.haman.dearme.ui.components.slider.StarSlider
import com.haman.dearme.ui.model.ScheduleModel
import com.haman.dearme.ui.model.item.ItemWithCountHeader
import com.haman.dearme.ui.model.plan.PlanCountModel
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.fullTimeFormat
import java.time.LocalDate
import java.time.YearMonth
import com.haman.dearme.util.ext.fullFormat

@Composable
fun MonthlyScreen(
    toDailyScreen: (YearMonth) -> Unit,
    toCategoryScreen: (YearMonth, Long) -> Unit,
    toChallengePostScreen: (Long) -> Unit,
    toSchedulePostScreen: (Long?) -> Unit,
    toScheduleAddScreen: (LocalDate) -> Unit,
    toDiaryScreen: (LocalDate) -> Unit,
    monthlyViewModel: MonthlyViewModel = hiltViewModel()
) {
    val calendarState = rememberCalendarState()
    LaunchedEffect(key1 = calendarState.monthState.currentMonth) {
        val date = calendarState.monthState.currentMonth
        monthlyViewModel.getMonthlyInfo(date.year, date.month.value)
    }
    LaunchedEffect(key1 = null) {
        monthlyViewModel.setSelectedDate(LocalDate.now())
    }
    val selectedDate = monthlyViewModel.selectedDate.collectAsState()

    val showAllCategoryFlag = remember { mutableStateOf(false) }

    val monthlyPlans = monthlyViewModel.monthlyPlan.collectAsState()
    val monthlyPlanByCategory = monthlyViewModel.monthlyPlanByCategory.collectAsState()
    val total = monthlyPlanByCategory.value.sumOf { it.value }
    val goodPoints = monthlyViewModel.goodPoints.collectAsState()
    val badPoints = monthlyViewModel.badPoints.collectAsState()

    val diary = monthlyViewModel.content.collectAsState()
    val diaryUiState = monthlyViewModel.diaryUiState.collectAsState()

    val rate = monthlyViewModel.rate.collectAsState()
    val challenges = monthlyViewModel.challenges.collectAsState()
    val allSchedules = monthlyViewModel.allSchedules.collectAsState()
    val schedules = monthlyViewModel.schedules.collectAsState()

    YearMonthAppBarContainer(
        yearMonth = calendarState.monthState.currentMonth,
        onDateChange = {
            calendarState.monthState.currentMonth = it
        },
        header = { BaseAppBar(title = "${calendarState.monthState.currentMonth.fullFormat()} 달력") }
    ) {
        LazyColumn(
            modifier = Modifier
                .background(
                    if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                    else ColorPalette.White
                )
        ) {
            itemWithBaseHeader(
                title = "이번달에는 무엇을 했을까?",
                {
                    paddingItem {
                        CalendarBody(
                            calendarState = calendarState,
                            planCount = monthlyPlans.value,
                            selectedDate = selectedDate.value,
                            schedules = allSchedules.value,
                            onClickDate = monthlyViewModel::setSelectedDate
                        )
                    }
                },
                {
                    paddingItem {
                        EvenSingleBarGraph(
                            dataAName = "완료한 업무",
                            dataBName = "완료하지 못한 업무",
                            dataAColor = ColorPalette.ACCENT,
                            dataBColor = ColorPalette.SECOND,
                            dataA = monthlyPlans.value.values.sumOf { it.completedPlan },
                            dataB = monthlyPlans.value.values.sumOf { it.noneCompletedPlan }
                        )
                    }
                }
            )
            ItemWithCountHeader(
                title = "${selectedDate.value.fullFormat()}에 등록된 일정",
                emptyMessage = "해당 날짜에 등록된 일정이 없습니다.",
                isEmpty = schedules.value.isEmpty(),
                size = schedules.value.size
            ) {
                paddingItem {
                    SchedulePager(
                        schedules = schedules.value,
                        onClickScheduleItem = toSchedulePostScreen,
                        onRemoveSchedule = monthlyViewModel::removeSchedule
                    )
                }
            }
            paddingItem {
                BaseClickableItem(
                    message = "일정 추가하기 >",
                    onClickItem = { toScheduleAddScreen(selectedDate.value) })
            }
            paddingItem {
                BaseClickableItem(
                    message = "Daily 기록 확인하기 >",
                    onClickItem = { toDiaryScreen(selectedDate.value) })
            }
            emptyDivider(12f)
            itemWithBaseHeader(
                title = "일별 통계",
                {
                    paddingItem(key = "graph") {
                        EvenListGraph(
                            total = calendarState.monthState.currentMonth.lengthOfMonth(),
                            counts = monthlyPlans.value
                        )
                    }
                },
                {
                    paddingItem {
                        BaseClickableItem(
                            message = "리스트로 확인하기 >",
                            onClickItem = { toDailyScreen(calendarState.monthState.currentMonth) })
                    }
                }
            )
            emptyDivider(2f)
            ItemWithCountHeader(
                title = "카테고리별 통계",
                emptyMessage = "등록된 업무가 없습니다.",
                isEmpty = monthlyPlanByCategory.value.isEmpty(),
                size = monthlyPlanByCategory.value.size,
                header = {
                    if (total > 0) {
                        CircleGraphWithTitle(
                            data = monthlyPlanByCategory.value,
                            title = "${total.fullTimeFormat()}\n${monthlyPlanByCategory.value.sumOf { it.count }}개",
                            total = total
                        )
                    }
                }
            ) {
                paddingItemsIndexed(
                    items = monthlyPlanByCategory.value,
                    key = { _, item -> item.categoryId }
                ) { index, item ->
                    if (showAllCategoryFlag.value || index < 5)
                        PlanByCategoryItem(
                            total = total.toFloat(),
                            category = item,
                            onClickCategoryItem = {
                                toCategoryScreen(calendarState.monthState.currentMonth, it)
                            }
                        )
                }
            }
            if (monthlyPlanByCategory.value.size > 5)
                paddingItem {
                    BaseClickableItem(
                        message = if (showAllCategoryFlag.value) "접기"
                        else "더보기",
                        arrangement = Arrangement.Center,
                        onClickItem = {
                            showAllCategoryFlag.value = showAllCategoryFlag.value.not()
                        })
                }


            emptyDivider(12f)
            ItemWithCountHeader(
                title = "챌린지",
                emptyMessage = "이번 달에는 등록된 챌린지가 없습니다.",
                isEmpty = challenges.value.isEmpty(),
                size = challenges.value.size
            ) {
                item {
                    LazyRow {
                        items(
                            items = challenges.value,
                            key = { item -> "challenge${item.id}" }) { item ->
                            ChallengeItem(
                                outerModifier = Modifier
                                    .width(300.dp)
                                    .padding(horizontal = 4.dp),
                                innerModifier = Modifier.height(150.dp),
                                challenge = item,
                                toPostScreen = toChallengePostScreen
                            )
                        }
                    }
                }
            }

            emptyDivider(12f)
            ItemWithCountHeader(
                title = "이건 잘했어!!",
                emptyMessage = "이번 달에는 등록된 사항이 없습니다.",
                isEmpty = goodPoints.value.isEmpty(),
                size = goodPoints.value.size
            ) {
                paddingItemsIndexed(
                    items = goodPoints.value,
                    key = { index, item -> "goods${item.id ?: index}" }
                ) { index, item ->
                    PointItem(
                        index = index,
                        point = item,
                        onRemovePoint = monthlyViewModel::removeGood
                    )
                }
            }

            emptyDivider(12f)
            ItemWithCountHeader(
                title = "이건 반성하자...",
                emptyMessage = "이번 달에는 등록된 사항이 없습니다.",
                isEmpty = badPoints.value.isEmpty(),
                size = badPoints.value.size
            ) {
                paddingItemsIndexed(
                    items = badPoints.value,
                    key = { index, item -> "bads${item.id ?: index}" }
                ) { index, item ->
                    PointItem(
                        index = index,
                        point = item,
                        onRemovePoint = monthlyViewModel::removeBad
                    )
                }
            }

            emptyDivider(12f)
            itemWithBaseHeader(
                title = "이번 달 내 점수는!",
                {
                    paddingItem {
                        StarSlider(rate = rate.value?.rate ?: 0, onClickRate = {
                            monthlyViewModel.changeRate(
                                calendarState.monthState.currentMonth,
                                it
                            )
                        })
                    }
                }, {
                    paddingItem {
                        SavableInput(
                            content = diary.value,
                            onChangeContent = {
                                monthlyViewModel.changeDiary(
                                    calendarState.monthState.currentMonth,
                                    it
                                )
                            },
                            diaryUiState = diaryUiState.value
                        )
                    }
                }
            )
            emptyDivider(64f)
            footer()
        }
    }
}

@Composable
fun CalendarBody(
    calendarState: CalendarState,
    planCount: Map<Int, PlanCountModel>,
    selectedDate: LocalDate,
    schedules: List<ScheduleModel>,
    onClickDate: (LocalDate) -> Unit
) {
    CalendarContainer(
        calendarState = calendarState,
        dayContent = {
            DefaultDate(
                planCount = planCount,
                state = it,
                schedules = schedules,
                selectedDate = selectedDate,
                onClickDate = onClickDate
            )
        }
    )
}