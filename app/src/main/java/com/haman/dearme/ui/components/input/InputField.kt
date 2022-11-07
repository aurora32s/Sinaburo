package com.haman.dearme.ui.components.input

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    name: String,
    textColor: Color = ColorPalette.DARK_BACKGROUND,
    input: @Composable () -> Unit
) {
    Row(
        modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Sub1(
            text = name,
            bold = true,
            modifier = Modifier.padding(4.dp),
            color = if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND,
        )
        Spacer(modifier = Modifier.width(4.dp))
        input()
    }
}