package com.haman.dearme.ui.screen.montly.schedule

import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haman.dearme.R
import com.haman.dearme.ui.components.calendar.CalendarContainer
import com.haman.dearme.ui.components.calendar.day.BaseDate
import com.haman.dearme.ui.components.calendar.day.DurationDate
import com.haman.dearme.ui.components.calendar.rememberCalendarState
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.header.PostAppBar
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.input.ContentInput
import com.haman.dearme.ui.components.input.InputField
import com.haman.dearme.ui.components.item.emptyDivider
import com.haman.dearme.ui.components.picker.WheelPicker
import com.haman.dearme.ui.components.picker.WheelTextPicker
import com.haman.dearme.ui.components.picker.WheelTimePicker
import com.haman.dearme.ui.components.text.Body1
import com.haman.dearme.ui.components.text.Body2
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.dateFormat
import com.haman.dearme.util.ext.fullFormat
import com.haman.dearme.util.ext.timeFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth

@Composable
fun ScheduleScreen(
    scheduleId: Long? = null,
    date: LocalDate? = null,
    onBackPressed: () -> Unit,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = null) {
        viewModel.init(scheduleId, date)

        viewModel.scheduleUiState.collect {
            when (it) {
                ScheduleUiState.Success.REMOVE_SCHEDULE -> {
                    onBackPressed()
                }
                else -> {}
            }
        }
    }

    val isModifyMode = viewModel.isModifyMode.collectAsState()
    val startedAt = viewModel.startedAt.collectAsState()
    val endedAt = viewModel.endedAt.collectAsState()

    val title = viewModel.title.collectAsState()
    val location = viewModel.location.collectAsState()
    val content = viewModel.content.collectAsState()
    val people = viewModel.people.collectAsState()
    val isImportant = viewModel.isImportant.collectAsState()

    Scaffold(
        topBar = {
            PostAppBar(
                title = "스케쥴 관리",
                isModifyMode = isModifyMode.value,
                onSave = viewModel::save,
                onCancel = {
                    viewModel.scheduleId?.let { viewModel.toggleModifyMode() } ?: onBackPressed()
                },
                onModifyMode = viewModel::toggleModifyMode,
                onBackPressed = onBackPressed,
                onDelete = viewModel::removeSchedule
            )
        }
    ) {
        SchedulePostBody(
            isModifyMode = isModifyMode.value,
            title = title.value,
            onTitleChanged = viewModel::changeTitle,
            content = content.value,
            onContentChanged = viewModel::changeContent,
            location = location.value,
            onLocationChanged = viewModel::changeLocation,
            people = people.value,
            onPeopleChanged = viewModel::changePeople,
            startedAt = startedAt.value,
            onStartedAtDateChange = viewModel::changeStartedAtDate,
            onStartedAtTimeChange = viewModel::changeStartedAtTime,
            endedAt = endedAt.value,
            onEndedAtDateChange = viewModel::changeEndedAtDate,
            onEndedAtTimeChange = viewModel::changeEndedAtTime,
            isImportant = isImportant.value,
            onClickImportantBtn = viewModel::changeIsImportant
        )
    }
}

@Composable
fun SchedulePostBody(
    isModifyMode: Boolean,
    title: String,
    onTitleChanged: (String) -> Unit,
    content: String,
    onContentChanged: (String) -> Unit,
    location: String,
    onLocationChanged: (String) -> Unit,
    people: String,
    onPeopleChanged: (String) -> Unit,
    startedAt: LocalDateTime,
    onStartedAtDateChange: (LocalDate) -> Unit,
    onStartedAtTimeChange: (LocalTime) -> Unit,
    endedAt: LocalDateTime,
    onEndedAtDateChange: (LocalDate) -> Unit,
    onEndedAtTimeChange: (LocalTime) -> Unit,
    isImportant: Boolean,
    onClickImportantBtn: () -> Unit
) {
    val type = remember { mutableStateOf(true) }
    val showCalendarFlag = remember { mutableStateOf(false) }
    val showTimePickerFlag = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isModifyMode) {
        showCalendarFlag.value = false
        showTimePickerFlag.value = false
    }

    LazyColumn(
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Sub1(
                    text = "\uD83D\uDD25 중요",
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(999.dp))
                        .background(
                            if (!isImportant) Color.Transparent
                            else ColorPalette.DARK_PRIMARY
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSystemInDarkTheme()) ColorPalette.GREY else ColorPalette.LIGHT_GREY,
                            shape = RoundedCornerShape(999.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 12.dp)
                        .clickable { if (isModifyMode) onClickImportantBtn() },
                    color = when {
                        isImportant || isSystemInDarkTheme() -> ColorPalette.White
                        else -> ColorPalette.DARK_BACKGROUND
                    }
                )
            }
        }
        item {
            ContentInput(
                name = "제목*",
                content = title,
                enabled = isModifyMode,
                onValueChange = onTitleChanged,
                singleLine = true,
                maxLength = 25
            )
        }
        item {
            Column {
                TimeItem(
                    isModifyMode = isModifyMode,
                    startedAt = startedAt,
                    endedAt = endedAt,
                    onDateChange = {
                        type.value = it
                        showCalendarFlag.value = true
                        showTimePickerFlag.value = false
                    },
                    onTimeChange = {
                        type.value = it
                        showCalendarFlag.value = false
                        showTimePickerFlag.value = true
                    }
                )
            }
        }
        if (showCalendarFlag.value) {
            item {
                DatePicker(
                    date = if (type.value) startedAt else endedAt,
                    onDateChanged = if (type.value) onStartedAtDateChange else onEndedAtDateChange
                )
            }
        }
        if (showTimePickerFlag.value) {
            item {
                Row {
                    WheelTimePicker(
                        modifier = Modifier.fillMaxWidth(),
                        startTime = if (type.value) startedAt.toLocalTime() else endedAt.toLocalTime(),
                        onScrollFinished = if (type.value) onStartedAtTimeChange else onEndedAtTimeChange
                    )
                }
            }
        }
        emptyDivider(12f)
        item {
            ContentInput(
                name = "위치",
                content = location,
                enabled = isModifyMode,
                onValueChange = onLocationChanged,
                maxLength = 50
            )
        }
        item {
            ContentInput(
                name = "메모",
                content = content,
                enabled = isModifyMode,
                onValueChange = onContentChanged
            )
        }
        item {
            ContentInput(
                name = "People",
                content = people,
                enabled = isModifyMode,
                onValueChange = onPeopleChanged,
                maxLength = 30
            )
        }
    }
}

@Composable
fun TimeItem(
    isModifyMode: Boolean,
    startedAt: LocalDateTime,
    endedAt: LocalDateTime,
    onDateChange: (Boolean) -> Unit,
    onTimeChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DateItem(
            isModifyMode = isModifyMode,
            date = startedAt,
            onDateChange = { onDateChange(true) },
            onTimeChange = { onTimeChange(true) }
        )
        Sub1(text = "~")
        DateItem(
            isModifyMode = isModifyMode,
            date = endedAt,
            onDateChange = { onDateChange(false) },
            onTimeChange = { onTimeChange(false) }
        )
    }
}

@Composable
fun DateItem(
    isModifyMode: Boolean,
    date: LocalDateTime,
    onDateChange: () -> Unit,
    onTimeChange: () -> Unit
) {
    Column(
        modifier = Modifier.padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Body1(
            text = date.dateFormat(),
            modifier = Modifier.clickable { if (isModifyMode) onDateChange() })
        Spacer(modifier = Modifier.height(4.dp))
        Body1(
            text = date.timeFormat(),
            modifier = Modifier.clickable { if (isModifyMode) onTimeChange() })
    }
}

@Composable
fun DatePicker(
    date: LocalDateTime,
    onDateChanged: (LocalDate) -> Unit
) {
    val calendarState =
        rememberCalendarState(initialMonth = YearMonth.of(date.year, date.month.value))

    LaunchedEffect(key1 = date) {
        calendarState.monthState.currentMonth = YearMonth.of(date.year, date.month.value)
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconImage(
                icon = R.drawable.left_arrow,
                color = if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND,
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        calendarState.monthState.currentMonth =
                            calendarState.monthState.currentMonth.minusMonths(1)
                    })
            Spacer(modifier = Modifier.width(4.dp))
            Sub1(text = calendarState.monthState.currentMonth.fullFormat())
            Spacer(modifier = Modifier.width(4.dp))
            IconImage(
                icon = R.drawable.right_arrow,
                color = if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND,
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        calendarState.monthState.currentMonth =
                            calendarState.monthState.currentMonth.plusMonths(1)
                    })
        }
        CalendarContainer(
            calendarState = calendarState,
            dayContent = {
                BaseDate(
                    currentDate = date.toLocalDate(),
                    state = it,
                    onClickDate = onDateChanged
                )
            }
        )
    }
}