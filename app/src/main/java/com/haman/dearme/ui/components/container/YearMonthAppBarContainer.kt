package com.haman.dearme.ui.components.container

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.R
import com.haman.dearme.ui.components.bottomsheet.YearMonthBottomSheet
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.selector.YearMonthSelector
import com.haman.dearme.ui.components.text.Body2
import com.haman.dearme.util.ext.fullFormat
import kotlinx.coroutines.launch
import java.time.YearMonth

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun YearMonthAppBarContainer(
    yearMonth: YearMonth,
    onDateChange: (YearMonth) -> Unit,
    header: @Composable () -> Unit,
    children: @Composable (modifier: Modifier) -> Unit
) {
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val date = remember { mutableStateOf(yearMonth) }
    val onChangeDate = { newDate: YearMonth ->
        date.value = newDate
        onDateChange(newDate)
    }

    CustomBottomSheet(
        sheetState = bottomSheetState,
        sheetContent = {
            YearMonthBottomSheet(
                date = date.value,
                onClickCloseBtn = {
                    coroutineScope.launch { bottomSheetState.hide() }
                },
                onChangeDate = {
                    onChangeDate(it)
                    coroutineScope.launch { bottomSheetState.hide() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = header
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                children(modifier = Modifier.fillMaxSize())
                YearMonthSelector(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    date = date.value,
                    onPrevMonth = { onChangeDate(date.value.minusMonths(1)) },
                    onNextMonth = { onChangeDate(date.value.plusMonths(1)) },
                    onClickMonth = {
                        coroutineScope.launch { bottomSheetState.show() }
                    }
                )
            }
        }
    }
}