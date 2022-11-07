package com.haman.dearme.ui.components.bottomsheet

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.NumberPicker
import com.haman.dearme.ui.components.picker.OutlinedNumberPicker
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.util.ext.dayOfWeekText
import com.haman.dearme.util.ext.getLastDate
import java.time.LocalDate

@Composable
fun FullDateSheetContent(
    modifier: Modifier = Modifier,
    date: LocalDate,
    onClickCloseBtn: () -> Unit,
    onChangeDate: (LocalDate) -> Unit
) {
    val selectedYear = remember { mutableStateOf(date.year) }
    val selectedMonth = remember { mutableStateOf(date.month.value) }
    val selectedDate = remember { mutableStateOf(date.dayOfMonth) }
    val lastDate =
        LocalDate.of(
            selectedYear.value,
            selectedMonth.value,
            1
        ).getLastDate()
    val dayOfWeek =
        LocalDate.of(
            selectedYear.value,
            selectedMonth.value,
            if (selectedDate.value > lastDate) lastDate else selectedDate.value
        ).dayOfWeekText()

    LaunchedEffect(key1 = date) {
        selectedYear.value = date.year
        selectedMonth.value = date.month.value
        selectedDate.value = date.dayOfMonth
    }
    BasicDateBottomSheet(
        modifier = modifier,
        onClickCloseBtn = onClickCloseBtn,
        onClickConfirmBtn = {
            onChangeDate(
                LocalDate.of(
                    selectedYear.value,
                    selectedMonth.value,
                    if (selectedDate.value > lastDate) lastDate else selectedDate.value
                )
            )
        }
    ) {
        NumberPicker(
            state = selectedYear,
            range = (LocalDate.now().year - 10)..(LocalDate.now().year + 10)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Sub1(text = "년")
        Spacer(modifier = Modifier.width(8.dp))
        NumberPicker(
            state = selectedMonth,
            range = 1..12
        )
        Spacer(modifier = Modifier.width(8.dp))
        Sub1(text = "월")
        Spacer(modifier = Modifier.width(8.dp))
        NumberPicker(
            state = selectedDate,
            range = 1..lastDate
        )
        Spacer(modifier = Modifier.width(8.dp))
        Sub1(text = "일")
        Spacer(modifier = Modifier.width(8.dp))
        Sub1(
            text = "${dayOfWeek}요일"
        )
    }
}