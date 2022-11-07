package com.haman.dearme.ui.components.text

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Body1(
    modifier: Modifier = Modifier,
    text: String,
    bold: Boolean = false,
    color: Color = MaterialTheme.colors.onSurface // 기본 black
) {
    CustomText(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.body1,
        bold = bold,
        color = color
    )
}

@Composable
fun Body2(
    modifier: Modifier = Modifier,
    text: String,
    bold: Boolean = false,
    color: Color = MaterialTheme.colors.onSurface // 기본 black
) {
    CustomText(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.body2,
        bold = bold,
        color = color
    )
}
