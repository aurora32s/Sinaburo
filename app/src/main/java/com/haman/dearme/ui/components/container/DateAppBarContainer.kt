package com.haman.dearme.ui.components.container

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.haman.dearme.ui.components.bottomsheet.FullDateSheetContent
import com.haman.dearme.ui.components.selector.DateSelector
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DateAppBarContainer(
    modifier: Modifier = Modifier,
    year: Int,
    month: Int,
    day: Int,
    onDateChange: (LocalDate) -> Unit,
    header: @Composable () -> Unit,
    children: @Composable (modifier: Modifier) -> Unit
) {
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val date = remember { mutableStateOf(LocalDate.of(year, month, day)) }

    val onChangeDate = { newDate: LocalDate ->
        date.value = newDate
        onDateChange(newDate)
    }

    CustomBottomSheet(
        sheetState = bottomSheetState,
        sheetContent = {
            FullDateSheetContent(
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
            Box(modifier = modifier.fillMaxSize()) {
                children(modifier = Modifier.fillMaxSize())
                DateSelector(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    date = date.value,
                    onPrevDate = { onChangeDate(date.value.minusDays(1)) },
                    onNextDate = { onChangeDate(date.value.plusDays(1)) },
                    onClickDate = {
                        coroutineScope.launch { bottomSheetState.show() }
                    }
                )
            }
        }
    }
}
