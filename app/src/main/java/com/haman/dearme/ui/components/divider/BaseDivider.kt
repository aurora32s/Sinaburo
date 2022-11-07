package com.haman.dearme.ui.components.divider

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun BaseDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 2.dp
) {
    Divider(
        color = (if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_SPACER).copy(
            alpha = 0.1f
        ),
        thickness = thickness,
        modifier = modifier
    )
}