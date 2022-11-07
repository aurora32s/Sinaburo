package com.haman.dearme.ui.components.bottomsheet

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.NumberPicker
import com.haman.dearme.ui.components.text.Sub1
import java.time.YearMonth

@Composable
fun YearMonthBottomSheet(
    modifier: Modifier = Modifier,
    date: YearMonth,
    onClickCloseBtn: () -> Unit,
    onChangeDate: (YearMonth) -> Unit
) {
    val selectedYear = remember { mutableStateOf(date.year) }
    val selectedMonth = remember { mutableStateOf(date.month.value) }

    LaunchedEffect(key1 = date) {
        selectedYear.value = date.year
        selectedMonth.value = date.month.value
    }
    BasicDateBottomSheet(
        modifier = modifier,
        onClickCloseBtn = onClickCloseBtn,
        onClickConfirmBtn = {
            onChangeDate(YearMonth.of(selectedYear.value, selectedMonth.value))
        }
    ) {
        NumberPicker(
            state = selectedYear,
            range = (YearMonth.now().year - 10)..(YearMonth.now().year + 10)
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
    }
}