package com.haman.dearme.ui.components.container

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.theme.ColorPalette

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomBottomSheet(
    sheetState: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    body: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = ColorPalette.Black.copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetContent = { sheetContent() }) {
        body()
    }
}