package com.haman.dearme.ui.components.canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.Line(
    color: Color,
    startX: Float, startY: Float,
    endX: Float, endY: Float,
    strokeWidth: Float = 2f
) {
    drawLine(
        color = color,
        start = Offset(startX, startY),
        end = Offset(endX, endY),
        strokeWidth = strokeWidth
    )
}