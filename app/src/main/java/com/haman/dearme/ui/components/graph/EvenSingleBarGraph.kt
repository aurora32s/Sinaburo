package com.haman.dearme.ui.components.graph

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haman.dearme.ui.components.canvas.text
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun EvenSingleBarGraph(
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

        val total = dataA + dataB
        if (total > 0) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .background(
                        if (isDarkTheme) ColorPalette.Black
                        else ColorPalette.White
                    )
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(5.dp),
                        color = if (isSystemInDarkTheme()) ColorPalette.DARK_GREY else ColorPalette.LIGHT_GREY
                    ),
                elevation = 2.dp
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    val padding = 100f
                    val realWidth = canvasWidth - padding
                    val halfHeight = canvasHeight / 2
                    val width = realWidth / total

                    val widthForA = dataA * width + ( if (dataB > 0) padding else 0f )

                    val dataAPath = Path().apply {
                        moveTo(padding, halfHeight - 40)
                        lineTo(widthForA + 10, halfHeight - 40)
                        lineTo(widthForA - 10, halfHeight + 40)
                        lineTo(padding, halfHeight + 40)
                        close()
                    }
                    val dataBPath = Path().apply {
                        moveTo(widthForA + 15, halfHeight - 40)
                        lineTo(realWidth, halfHeight - 40)
                        lineTo(realWidth, halfHeight + 40)
                        lineTo(widthForA -5, halfHeight + 40)
                        close()
                    }
                    drawIntoCanvas {
                        if (dataA > 0) {
                            it.drawOutline(
                                outline = Outline.Generic(dataAPath),
                                paint = Paint().apply {
                                    color = dataAColor
                                    pathEffect = PathEffect.cornerPathEffect(15f)
                                }
                            )
                        }
                        if (dataB > 0) {
                            it.drawOutline(
                                outline = Outline.Generic(dataBPath),
                                paint = Paint().apply {
                                    color = dataBColor
                                    pathEffect = PathEffect.cornerPathEffect(15f)
                                }
                            )
                        }
                        it.text(
                            context = context,
                            text = dataA.toString(),
                            x = padding / 2, y = halfHeight,
                            android.graphics.Paint().apply {
                                textSize = (12.sp).toPx()
                                color = if (isDarkTheme) android.graphics.Color.WHITE
                                else android.graphics.Color.BLACK
                            }
                        )
                        it.text(
                            context = context,
                            text = dataB.toString(),
                            x = canvasWidth - padding / 2 - 10, y = halfHeight,
                            android.graphics.Paint().apply {
                                textSize = (12.sp).toPx()
                                color = if (isDarkTheme) android.graphics.Color.WHITE
                                else android.graphics.Color.BLACK
                            }
                        )
                    }
                }
            }
        }
    }
}