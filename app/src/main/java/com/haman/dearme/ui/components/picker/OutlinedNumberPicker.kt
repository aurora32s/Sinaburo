package com.haman.dearme.ui.components.picker

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.NumberPicker
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun OutlinedNumberPicker(
    state: MutableState<Int>,
    minValue: Int,
    maxValue: Int,
    spacer: Int,
    text: String,
    textColor: Color = ColorPalette.Black
) {
    NumberPicker(
        state = state,
        range = minValue..maxValue
    )
    Spacer(modifier = Modifier.width(spacer.dp))
    Sub1(text = text, color = textColor)
    Spacer(modifier = Modifier.width(spacer.dp))
}