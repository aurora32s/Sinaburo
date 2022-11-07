package com.haman.dearme.ui.components.graph

import android.content.Context
import android.graphics.Paint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haman.dearme.ui.components.canvas.Line
import com.haman.dearme.ui.components.canvas.rectangle
import com.haman.dearme.ui.components.canvas.text
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.model.plan.PlanCountModel
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun EvenListGraph(
    context: Context = LocalContext.current,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    total: Int,
    counts: Map<Int, PlanCountModel>
) {
    val maxCount =
        counts.values.map { Integer.max(it.completedPlan, it.noneCompletedPlan) }
            .maxOfOrNull { it }
            ?: 1
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isDarkTheme) ColorPalette.DARK_BACKGROUND
                else ColorPalette.White
            )
            .height(150.dp)
            .padding(horizontal = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(ColorPalette.ACCENT)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Sub1(text = "완료한 업무")
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(ColorPalette.SECOND)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Sub1(text = "완료하지 못한 업무")
        }
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .width((46.dp) * total)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val height = (canvasHeight - 140) / maxCount
                var y = 50f

                Line(
                    color = if (isDarkTheme) ColorPalette.White else ColorPalette.DARK_BACKGROUND,
                    startX = 0f, startY = canvasHeight - 40,
                    endX = canvasWidth, endY = canvasHeight - 40
                )

                (1..total).forEach { day ->
                    val completedPlan = counts[day]?.completedPlan ?: 0
                    val noneCompletedPlan = counts[day]?.noneCompletedPlan ?: 0
                    rectangle(
                        color = ColorPalette.ACCENT,
                        x = y - 50, y = canvasHeight - completedPlan * height - 40,
                        width = 50f, height = completedPlan * height
                    )
                    rectangle(
                        color = ColorPalette.SECOND,
                        x = y, y = canvasHeight - noneCompletedPlan * height - 40,
                        width = 50f, height = noneCompletedPlan * height
                    )
                    drawIntoCanvas {
                        it.text(
                            context = context,
                            text = completedPlan.toString(),
                            x = y - 30, canvasHeight - completedPlan * height - 50,
                            Paint().apply {
                                textSize = (10.sp).toPx()
                                color = if (isDarkTheme) android.graphics.Color.WHITE
                                else android.graphics.Color.BLACK
                            }
                        )
                        it.text(
                            context = context,
                            text = noneCompletedPlan.toString(),
                            x = y + 15, canvasHeight - noneCompletedPlan * height - 50,
                            Paint().apply {
                                textSize = (10.sp).toPx()
                                color = if (isDarkTheme) android.graphics.Color.WHITE
                                else android.graphics.Color.BLACK
                            }
                        )
                        it.text(
                            context = context,
                            text = "${day}일",
                            x = y - 15, canvasHeight - 10,
                            Paint().apply {
                                textSize = (10.sp).toPx()
                                color = if (isDarkTheme) android.graphics.Color.WHITE
                                else android.graphics.Color.BLACK
                            }
                        )
                    }
                    y += 120
                }
            }
        }
    }
}