package com.haman.dearme.ui.components.canvas

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.rectangle(
    color: Color,
    x: Float,
    y: Float,
    width: Float,
    height: Float
) {
    drawRect(
        color = color,
        topLeft = Offset(x, y),
        size = Size(width, height)
    )
}