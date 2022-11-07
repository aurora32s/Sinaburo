package com.haman.dearme.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.haman.dearme.R
import com.haman.dearme.ui.components.common.FullSpace
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.model.ScheduleModel
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.fullTimeFormat

@Composable
fun ScheduleMainItem(
    schedule: ScheduleModel,
    onClickScheduleItem: (Long) -> Unit,
    onRemoveSchedule: (Long) -> Unit
) {
    val showScheduleContent = rememberSaveable { mutableStateOf(false) }
    ScheduleBody(
        schedule = schedule,
        showScheduleContent = showScheduleContent.value,
        onClickShowScheduleContent = {
            showScheduleContent.value = showScheduleContent.value.not()
        },
        onClickScheduleItem = onClickScheduleItem,
        onRemoveSchedule = onRemoveSchedule
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SchedulePager(
    schedules: List<ScheduleModel>,
    onClickScheduleItem: (Long) -> Unit,
    onRemoveSchedule: (Long) -> Unit
) {
    val pageState = rememberPagerState()
    val showScheduleDetail = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.background(
            if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
            else ColorPalette.White
        )
    ) {
        Sub1(
            modifier = Modifier.align(Alignment.End),
            text = "${pageState.currentPage + 1}/${schedules.size}"
        )
        HorizontalPager(
            count = schedules.size,
            state = pageState
        ) { page ->
            val schedule = schedules[page]
            ScheduleBody(
                schedule = schedule,
                showScheduleContent = showScheduleDetail.value,
                onClickShowScheduleContent = {
                    showScheduleDetail.value = showScheduleDetail.value.not()
                },
                onClickScheduleItem = onClickScheduleItem,
                onRemoveSchedule = onRemoveSchedule
            )
        }
    }
}

@Composable
fun ScheduleBody(
    schedule: ScheduleModel,
    showScheduleContent: Boolean,
    onClickShowScheduleContent: () -> Unit,
    onClickScheduleItem: (Long) -> Unit,
    onRemoveSchedule: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .border(
                color = if (isSystemInDarkTheme()) ColorPalette.White.copy(alpha = 0.2f)
                else ColorPalette.GREY,
                shape = RoundedCornerShape(6.dp),
                width = (1.5).dp
            )
            .clip(RoundedCornerShape(6.dp))
            .background(
                if (isSystemInDarkTheme()) ColorPalette.Black
                else ColorPalette.White
            )
            .clickable { onClickScheduleItem(schedule.id!!) }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Sub1(text = if (schedule.important) "\uD83D\uDD25중요" else "\uD83C\uDF7F일반")
            FullSpace()
            IconImage(
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onRemoveSchedule(schedule.id!!) },
                icon = R.drawable.trash,
                color = if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND
            )
        }
        Sub1(text = schedule.title)
        Sub1(text = "${schedule.startedAt.fullTimeFormat()} ~ ${schedule.endedAt.fullTimeFormat()}")

        BaseDivider()
        if (showScheduleContent) {
            Spacer(modifier = Modifier.height(4.dp))
            Sub2(text = "위치 : ${schedule.location}")
            Sub2(text = "메모 : ${schedule.content}")
            Sub2(text = "with ${schedule.people}")
        }
        Row(modifier = Modifier
            .clickable { onClickShowScheduleContent() }
            .fillMaxWidth()
            .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Sub2(
                text = if (showScheduleContent)
                    "내용접기"
                else "내용보기",
            )
        }
    }
}