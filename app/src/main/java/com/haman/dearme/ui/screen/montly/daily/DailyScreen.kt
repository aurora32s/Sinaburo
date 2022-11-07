package com.haman.dearme.ui.screen.montly.daily

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haman.dearme.R
import com.haman.dearme.ui.components.appbar.OneBtnAppBar
import com.haman.dearme.ui.components.chip.RectangleChip
import com.haman.dearme.ui.components.common.EmptyListItem
import com.haman.dearme.ui.components.common.footer
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.graph.EvenListGraph
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.item.emptyDivider
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.model.plan.PlanCountModel
import com.haman.dearme.ui.model.plan.PlanDiaryModel
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.fullFormat
import com.haman.dearme.util.ext.fullTimeFormat
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DailyScreen(
    year: Int,
    month: Int,
    onBackPressed: () -> Unit,
    viewModel: DailyViewModel = hiltViewModel(),
    toPostScreen: (Long) -> Unit,
    toDiaryScreen: (LocalDate) -> Unit
) {
    val yearMonth = YearMonth.of(year, month)

    LaunchedEffect(key1 = null) {
        viewModel.init(year, month)
    }
    val monthlyPlanCount = viewModel.monthlyPlanCount.collectAsState()
    val monthlyPlan = viewModel.monthlyPlan.collectAsState()

    Scaffold(
        topBar = {
            OneBtnAppBar(title = yearMonth.fullFormat()) {
                IconImage(
                    icon = R.drawable.side_arrow,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onBackPressed() },
                    color = if (isSystemInDarkTheme()) ColorPalette.White
                    else ColorPalette.DARK_BACKGROUND
                )
            }
        }
    ) {
        Column {
            EvenListGraph(
                total = yearMonth.lengthOfMonth(),
                counts = monthlyPlanCount.value
            )
            LazyColumn(
                modifier = Modifier
                    .background(
                        if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                        else ColorPalette.White
                    )
                    .padding(top = 8.dp)
            ) {
                if (monthlyPlan.value.isEmpty()) {
                    item { EmptyListItem(message = "해당하는 일정 정보가 없습니다.") }
                } else {
                    monthlyPlan.value.forEach { day, plans ->
                        stickyHeader {
                            DailyHeader(
                                day = day,
                                planCount = monthlyPlanCount.value[day.dayOfMonth],
                                plans = plans,
                                toDiaryScreen = toDiaryScreen
                            )
                        }
                        items(items = plans, key = { item -> item.id }) {
                            PlanListItem(plan = it, onClickItem = toPostScreen)
                        }
                    }
                }
                emptyDivider(230f)
                footer()
            }
        }
    }
}

@Composable
fun DailyHeader(
    day: LocalDate,
    planCount: PlanCountModel?,
    plans: List<PlanDiaryModel>,
    toDiaryScreen: (LocalDate) -> Unit
) {
    val total = plans.sumOf { it.value }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                else ColorPalette.White
            )
            .padding(top = 16.dp, end = 12.dp, bottom = 4.dp, start = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Sub1(text = day.fullFormat())
            Spacer(modifier = Modifier.width(4.dp))
            planCount?.let {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(ColorPalette.ACCENT)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Sub1(text = "${planCount.completedPlan}개")
                Spacer(modifier = Modifier.width(2.dp))
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(ColorPalette.SECOND)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Sub1(text = "${planCount.noneCompletedPlan}개")
            }
            Spacer(modifier = Modifier.weight(1f))
            Sub2(text = "자세히 보기 >", modifier = Modifier.clickable { toDiaryScreen(day) })
        }
        Spacer(modifier = Modifier.height(4.dp))
        BaseDivider()
        if (total > 0) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(29.dp)
                    .padding(vertical = 4.dp)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val width = canvasWidth / total
                var x = 0f
                plans.forEach { plan ->
                    drawRect(
                        color = plan.color,
                        topLeft = Offset(x = x, y = 0f),
                        size = Size(
                            width = plan.value * width,
                            height = 70f
                        )
                    )
                    x += plan.value * width
                    drawLine(
                        color = ColorPalette.Black,
                        start = Offset(x = x, y = 0f),
                        end = Offset(x = x, y = 70f)
                    )
                }
            }
        }
    }
}

@Composable
fun PlanListItem(
    modifier: Modifier = Modifier,
    plan: PlanDiaryModel,
    onClickItem: (Long) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .border(
                width = (1.5).dp,
                color = plan.color,
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    bottomStart = 10.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .clickable { onClickItem(plan.id) }
            .background(Color.Transparent)
            .padding(horizontal = 10.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RectangleChip(
            text = plan.categoryName,
            color = plan.color,
            modifier = Modifier.widthIn(min = 10.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Sub1(
            text = plan.title
        )
        Spacer(modifier = Modifier.weight(1f))
        Sub1(text = plan.value.fullTimeFormat())
        Spacer(modifier = Modifier.width(4.dp))
        Sub1(text = ">")
    }
}