package com.haman.dearme.ui.components.graph

import android.content.Context
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.theme.ColorPalette
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.haman.dearme.ui.components.canvas.Line
import com.haman.dearme.ui.components.canvas.rectangle
import com.haman.dearme.ui.components.canvas.text
import com.haman.dearme.ui.components.text.Sub1

@Composable
fun EvenBarGraph(
    context: Context = LocalContext.current,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    dataAName: String,
    dataBName: String,
    dataAColor: Color,
    dataBColor: Color,
    dataA: Int,
    dataB: Int
) {
    Column(
        modifier = Modifier.background(
            if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
            else ColorPalette.White
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(dataAColor)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Sub1(text = dataAName)
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(dataBColor)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Sub1(text = dataBName)
        }
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            val canvasHeight = size.height
            val canvasWidth = size.width

            val width = (canvasWidth - 50) / maxOf(dataA, dataB)
            rectangle(dataAColor, 50f, canvasHeight / 2 - 55, dataA * width, 50f)
            rectangle(dataBColor, 50f, canvasHeight / 2 + 5, dataB * width, 50f)

            drawIntoCanvas {
                it.text(
                    context = context,
                    text = dataA.toString(),
                    x = 0f, y = canvasHeight / 2 - 25,
                    Paint().apply {
                        textSize = (12.sp).toPx()
                        color = if (isDarkTheme) android.graphics.Color.WHITE
                        else android.graphics.Color.BLACK
                    }
                )
                it.text(
                    context = context,
                    text = dataB.toString(),
                    x = 0f, y = canvasHeight / 2 + 35,
                    Paint().apply {
                        textSize = (12.sp).toPx()
                        color = if (isDarkTheme) android.graphics.Color.WHITE
                        else android.graphics.Color.BLACK
                    }
                )
            }
        }
    }
}