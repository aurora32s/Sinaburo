package com.haman.dearme.ui.screen.montly.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haman.dearme.R
import com.haman.dearme.ui.components.appbar.OneBtnAppBar
import com.haman.dearme.ui.components.common.EmptyListItem
import com.haman.dearme.ui.components.common.footer
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.graph.EvenListGraph
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.item.emptyDivider
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.model.plan.PlanCountModel
import com.haman.dearme.ui.screen.montly.daily.PlanListItem
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.fullFormat
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryScreen(
    year: Int,
    month: Int,
    category: Long,
    onBackPressed: () -> Unit,
    toPostScreen: (Long) -> Unit,
    toDiaryScreen: (LocalDate) -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val yearMonth = YearMonth.of(year, month)

    LaunchedEffect(key1 = null) {
        viewModel.init(year, month, category)
    }

    val monthlyPlanCount = viewModel.monthlyPlanCount.collectAsState()
    val monthlyPlan = viewModel.monthlyPlan.collectAsState()

    Scaffold(
        topBar = {
            OneBtnAppBar(title = "${yearMonth.fullFormat()} / ") {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isSystemInDarkTheme()) ColorPalette.DARK_SPACER
                    else ColorPalette.SPACER
                )
        ) {
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
                            CategoryDailyHeader(
                                day = day,
                                planCount = monthlyPlanCount.value[day.dayOfMonth],
                                toDiaryScreen = toDiaryScreen
                            )
                        }
                        items(items = plans, key = { item -> item.id }) { plan ->
                            PlanListItem(
                                plan = plan,
                                onClickItem = { toPostScreen(plan.id) }
                            )
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
fun CategoryDailyHeader(
    day: LocalDate,
    planCount: PlanCountModel?,
    toDiaryScreen: (LocalDate) -> Unit
) {
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
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
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
            Sub1(text = "자세히 보기 >", modifier = Modifier.clickable { toDiaryScreen(day) })
        }
        BaseDivider()
    }
}
