package com.haman.dearme.ui.components.canvas

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.nativeCanvas
import com.haman.dearme.R


fun Canvas.text(
    context: Context,
    text: String,
    x: Float,
    y: Float,
    paint: Paint
) {
    nativeCanvas.drawText(
        text, x, y,
        paint.apply {
            typeface = Typeface.createFromAsset(context.assets, "bm_dohyeon.ttf")
        }
    )
}