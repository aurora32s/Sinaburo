package com.haman.dearme.ui.components.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.haman.dearme.ui.model.BaseCircleGraphModel
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.text.Body2

private const val DividerLengthInDegrees = 0f

private enum class AnimatedCircleProgress { START, END }

@Composable
fun CircleGraph(
    modifier: Modifier = Modifier,
    data: List<BaseCircleGraphModel>,
    totalCount: Long
) {
    val currentState = remember {
        MutableTransitionState(AnimatedCircleProgress.START)
            .apply { targetState = AnimatedCircleProgress.END }
    }
    val stroke = with(LocalDensity.current) { Stroke(20.dp.toPx()) }
    val transition = updateTransition(currentState, label = "")
//    val angleOffset by transition.animateFloat(
//        transitionSpec = {
//            tween(
//                delayMillis = 500,
//                durationMillis = 900,
//                easing = LinearOutSlowInEasing
//            )
//        }, label = ""
//    ) { progress ->
//        if (progress == AnimatedCircleProgress.START) {
//            0f
//        } else {
//            360f
//        }
// }

    Canvas(modifier = modifier) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - innerRadius,
            halfSize.height - innerRadius
        )
        val size = Size(innerRadius * 2, innerRadius * 2)
        var startAngle = -90f
        data.forEachIndexed { index, rate ->
            val sweep = (rate.value / totalCount.toFloat()) * 360
            drawArc(
                color = rate.color,
                startAngle = startAngle + DividerLengthInDegrees,
                sweepAngle = sweep - DividerLengthInDegrees,
                topLeft = topLeft,
                size = size,
                useCenter = false,
                style = stroke
            )
            startAngle += sweep
        }
    }
}