package com.haman.dearme.ui.screen.diary

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.haman.dearme.ui.MainViewModel
import com.haman.dearme.ui.components.common.footer
import com.haman.dearme.ui.components.container.DateAppBarContainer
import com.haman.dearme.ui.components.graph.CircleGraphWithTitle
import com.haman.dearme.ui.components.graph.EvenBarGraph
import com.haman.dearme.ui.components.header.BaseAppBar
import com.haman.dearme.ui.components.input.SavableInput
import com.haman.dearme.ui.components.item.*
import com.haman.dearme.ui.components.slider.ImageSlider
import com.haman.dearme.ui.components.slider.StarSlider
import com.haman.dearme.ui.model.*
import com.haman.dearme.ui.model.item.ItemWithCountHeader
import com.haman.dearme.ui.model.plan.PlanDiaryModel
import com.haman.dearme.ui.model.plan.PlanItem
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.fullTimeFormat

@Composable
fun DiaryScreen(
    mainViewModel: MainViewModel,
    viewModel: DiaryViewModel = hiltViewModel(),
    toPostScreen: (Long) -> Unit,
    toScheduleScreen: (Long?) -> Unit,
    toChallengePostScreen: (Long) -> Unit
) {
    val year = mainViewModel.year.collectAsState()
    val month = mainViewModel.month.collectAsState()
    val day = mainViewModel.day.collectAsState()

    LaunchedEffect(key1 = year.value, key2 = month.value, key3 = day.value) {
        viewModel.getDiaryInfo(year.value, month.value, day.value)
    }

    val planCount = viewModel.allCount.collectAsState()
    val diary = viewModel.content.collectAsState()
    val diaryState = viewModel.diaryUiState.collectAsState()
    val completedPlans = viewModel.completedPlans.collectAsState()
    val goods = viewModel.goods
    val bads = viewModel.bads
    val galleryImage = viewModel.galleryImage.collectAsState()
    val rate = viewModel.rate.collectAsState()
    val challenges = viewModel.challenges.collectAsState()
    val schedules = viewModel.schedules.collectAsState()

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
        header = { BaseAppBar(title = "오늘의 기록") }
    ) {
        DiaryBody(
            completedPlan = completedPlans.value,
            onCompletedClick = toPostScreen,
            goods = goods,
            bads = bads,
            onRemoveGood = viewModel::removeGood,
            onRemoveBad = viewModel::removeBad,
            addGoodPoint = { viewModel.addGood(it, year.value, month.value, day.value) },
            addBadPoint = { viewModel.addBad(it, year.value, month.value, day.value) },
            diary = diary.value,
            diaryState = diaryState.value,
            onDiaryChange = { viewModel.changeDiary(year.value, month.value, day.value, it) },
            images = galleryImage.value,
            planCount = planCount.value,
            rate = rate.value,
            onChangeRate = { viewModel.changeRate(year.value, month.value, day.value, it) },
            challenges = challenges.value,
            toChallengePostScreen = toChallengePostScreen,
            schedules = schedules.value,
            toScheduleScreen = toScheduleScreen,
            onRemoveSchedule = viewModel::removeSchedule
        )
    }
}

@Composable
fun DiaryBody(
    completedPlan: List<PlanDiaryModel>,
    onCompletedClick: (Long) -> Unit,
    goods: List<PointModel>,
    bads: List<PointModel>,
    onRemoveGood: (Long) -> Unit,
    onRemoveBad: (Long) -> Unit,
    addGoodPoint: (String) -> Unit,
    addBadPoint: (String) -> Unit,
    diary: String,
    diaryState: DiaryUiState,
    onDiaryChange: (String) -> Unit,
    images: List<Uri>,
    planCount: Int,
    rate: RateModel?,
    onChangeRate: (Int) -> Unit,
    challenges: List<MainChallengeModel>,
    toChallengePostScreen: (Long) -> Unit,
    schedules: List<ScheduleModel>,
    toScheduleScreen: (Long?) -> Unit,
    onRemoveSchedule: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .background(
                if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                else ColorPalette.White
            )
    ) {
        // 오늘 한일 그래프
        ItemWithCountHeader(
            title = "오늘 완료한 업무",
            emptyMessage = "완료한 업무가 없습니다.",
            isEmpty = completedPlan.isEmpty(),
            size = completedPlan.size,
            header = {
                // 한 일 총 소요시간 별 원형 그래프
                val total = completedPlan.sumOf { it.value }
                CircleGraphWithTitle(
                    data = completedPlan,
                    title = total.fullTimeFormat(),
                    total = total
                )
            }
        ) {
            paddingItems(
                items = completedPlan,
                key = { item -> "todo${item.id}" }
            ) {
                DiaryPlanItem(plan = it, onClickPlanItem = onCompletedClick)
            }
        }
        emptyDivider(12f)
        ItemWithCountHeader(
            title = "오늘의 일정",
            emptyMessage = "등록된 일정이 없습니다.",
            isEmpty = schedules.isEmpty(),
            size = schedules.size
        ) {
            paddingItem {
                SchedulePager(
                    schedules = schedules,
                    onClickScheduleItem = toScheduleScreen,
                    onRemoveSchedule = onRemoveSchedule
                )
            }
        }
        emptyDivider(12f)
        ItemWithCountHeader(
            title = "오늘의 챌린지",
            emptyMessage = "등록된 챌린지가 없습니다.",
            isEmpty = challenges.isEmpty(),
            size = challenges.size
        ) {
            paddingItems(
                items = challenges,
                key = { item -> "challenge${item.id}" }
            ) {
                CheckableChallengeItem(
                    challenge = it,
                    completed = it.count > 0,
                    onClickChallengeItem = toChallengePostScreen,
                    onCompleteChallenge = {}
                )
            }
        }
        emptyDivider(12f)
        itemWithBaseHeader(
            title = "오늘 하루는...",
            { if (images.isEmpty().not()) paddingItem { ImageSlider(images = images) } },
            {
                paddingItem {
                    EvenBarGraph(
                        dataAName = "완료한 업무",
                        dataBName = "완료하지 못한 업무",
                        dataAColor = ColorPalette.ACCENT,
                        dataBColor = ColorPalette.SECOND,
                        dataA = completedPlan.size,
                        dataB = planCount - completedPlan.size
                    )
                }
            }
        )
        emptyDivider(6f)
        itemWithBaseHeader(
            title = "오늘 내 점수는!",
            { paddingItem { StarSlider(rate = rate?.rate ?: 0, onClickRate = onChangeRate) } },
            {
                paddingItem {
                    SavableInput(
                        content = diary,
                        onChangeContent = onDiaryChange,
                        diaryUiState = diaryState
                    )
                }
            }
        )
        emptyDivider(6f)
        addableItemWithCount(
            title = "이건 잘했어!!",
            emptyMessage = "등록된 사항 없습니다.",
            isEmpty = goods.isEmpty(),
            size = goods.size,
            onAddItem = addGoodPoint
        ) {
            paddingItemsIndexed(
                items = goods,
                key = { _, item -> "goods${item.id}" }
            ) { index, item ->
                PointItem(point = item, index = index, onRemovePoint = onRemoveGood)
            }
        }
        emptyDivider(16f)
        addableItemWithCount(
            title = "이건 반성하자...",
            emptyMessage = "등록된 사항 없습니다.",
            isEmpty = bads.isEmpty(),
            size = bads.size,
            onAddItem = addBadPoint
        ) {
            paddingItemsIndexed(
                items = bads,
                key = { _, item -> "bads${item.id}" }
            ) { index, item ->
                PointItem(point = item, index = index, onRemovePoint = onRemoveBad)
            }
        }
        emptyDivider(64f)
        footer()
    }
}