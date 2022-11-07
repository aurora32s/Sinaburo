package com.haman.dearme.ui.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.haman.dearme.ui.MainViewModel
import com.haman.dearme.ui.components.common.footer
import com.haman.dearme.ui.components.container.DateAppBarContainer
import com.haman.dearme.ui.components.header.AddAppBar
import com.haman.dearme.ui.components.item.CheckableChallengeItem
import com.haman.dearme.ui.components.item.ScheduleMainItem
import com.haman.dearme.ui.components.item.emptyDivider
import com.haman.dearme.ui.components.item.paddingItems
import com.haman.dearme.ui.model.item.ItemWithCountHeader
import com.haman.dearme.ui.model.item.RemovableItem
import com.haman.dearme.ui.model.plan.PlanItem
import com.haman.dearme.ui.theme.ColorPalette
import java.time.LocalDate

@Composable
fun HistoryScreen(
    mainViewModel: MainViewModel,
    viewModel: HistoryViewModel = hiltViewModel(),
    toPostScreen: (Long?) -> Unit,
    toScheduleScreen: (Long?) -> Unit,
    toPostScheduleScreen: (LocalDate) -> Unit,
    toChallengePostScreen: (Long) -> Unit
) {
    val year = mainViewModel.year.collectAsState()
    val month = mainViewModel.month.collectAsState()
    val day = mainViewModel.day.collectAsState()

    val plans = viewModel.plans // 오늘의 일정
    val challenges = viewModel.challenge.collectAsState() // 오늘의 도전과제
    val completedChallenges = viewModel.completedChallenge // 완료한 도전과제 리스트
    val schedules = viewModel.schedules.collectAsState() // 오늘의 일정

    LaunchedEffect(key1 = year.value, key2 = month.value, key3 = day.value) {
        // 날짜가 변경될 때마다 서버로부터 해당 날짜의 데이터 요청
        viewModel.getPlans(year.value, month.value, day.value)
    }

    DateAppBarContainer(
        modifier = Modifier
            .background(
                if (isSystemInDarkTheme()) ColorPalette.DARK_SPACER
                else ColorPalette.SPACER
            ),
        year = year.value,
        month = month.value,
        day = day.value,
        onDateChange = { mainViewModel.setDate(it.year, it.month.value, it.dayOfMonth) },
        header = {
            AddAppBar(options = listOf("업무 추가", "일정 추가")) {
                when (it) {
                    0 -> {
                        toPostScreen(null)
                    } // 일정 추가하기
                    1 -> {
                        toPostScheduleScreen(LocalDate.of(year.value, month.value, day.value))
                    } // to do 추가하기
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .background(
                    if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                    else ColorPalette.White
                )
        ) {
            ItemWithCountHeader(
                title = "오늘의 일정",
                emptyMessage = "등록된 일정이 없습니다.",
                isEmpty = schedules.value.isEmpty(),
                size = schedules.value.size,
                body = {
                    paddingItems(
                        items = schedules.value,
                        key = { item -> "schedule${item.id}" }
                    ) {
                        ScheduleMainItem(
                            schedule = it,
                            onClickScheduleItem = toScheduleScreen,
                            onRemoveSchedule = viewModel::removeSchedule
                        )
                    }
                }
            )
            emptyDivider(6f)
            ItemWithCountHeader(
                title = "오늘의 업무",
                emptyMessage = "등록된 업무가 없습니다.",
                isEmpty = plans.isEmpty(),
                size = plans.size,
            ) {
                paddingItems(
                    items = plans,
                    key = { item -> "todo${item.id}" }
                ) {
                    RemovableItem(
                        item = { PlanItem(plan = it, onClickPlanItem = toPostScreen) },
                        dismissColor = it.categoryColor,
                        onRemoveItem = { viewModel.removePlans(it.id) })
                }
            }
            emptyDivider(16f)
            ItemWithCountHeader(
                title = "오늘의 챌린지",
                emptyMessage = "등록된 챌린지가 없습니다",
                isEmpty = challenges.value.isEmpty(),
                size = challenges.value.size
            ) {
                paddingItems(
                    items = challenges.value,
                    key = { item -> "challenge${item.id}" }
                ) {
                    CheckableChallengeItem(
                        challenge = it,
                        completed = it.id in completedChallenges,
                        onClickChallengeItem = toChallengePostScreen,
                        onCompleteChallenge = {
                            viewModel.completedChallenge(
                                it,
                                year.value,
                                month.value,
                                day.value
                            )
                        }
                    )
                }
            }
            emptyDivider(64f)
            footer()
        }
    }
}