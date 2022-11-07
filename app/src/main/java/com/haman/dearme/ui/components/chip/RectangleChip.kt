package com.haman.dearme.ui.components.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun RectangleChip(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = ColorPalette.White,
    color: Color = MaterialTheme.colors.primary
) {
    Sub1(
        text = text,
        color = textColor,
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(color = color)
            .widthIn(56.dp)
            .padding(horizontal = 2.dp, vertical = 3.dp)
    )
}