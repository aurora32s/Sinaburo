package com.haman.dearme.ui.components.text

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Sub1(
    modifier: Modifier = Modifier,
    text: String,
    bold: Boolean = false,
    align: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colors.onSurface // 기본 black
) {
    CustomText(
        modifier = modifier,
        text = text,
        align = align,
        style = MaterialTheme.typography.subtitle1,
        bold = bold,
        color = color
    )
}

@Composable
fun Sub2(
    modifier: Modifier = Modifier,
    text: String,
    bold: Boolean = false,
    align: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colors.onSurface // 기본 black
) {
    CustomText(
        modifier = modifier,
        text = text,
        align = align,
        style = MaterialTheme.typography.subtitle2,
        bold = bold,
        color = color
    )
}
