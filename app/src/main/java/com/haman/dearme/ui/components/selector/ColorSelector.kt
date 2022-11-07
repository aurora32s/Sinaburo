package com.haman.dearme.ui.components.selector

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.lang.Integer.min

@Composable
fun ColorSelector(
    modifier: Modifier = Modifier,
    colors: List<Long>,
    perLine: Int,
    selectedColor: Long,
    onSelectedItem: (Long) -> Unit
) {
    var rowNum = colors.size / perLine
    if (colors.size % perLine != 0) rowNum++

    Column {
        (0 until rowNum).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                (0 until min(perLine, colors.size)).forEach { column ->
                    val color = colors[row * perLine + column]
                    val paddingAnimation =
                        animateDpAsState(targetValue = if (color == selectedColor) 0.dp else 4.dp)
                    Spacer(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(paddingAnimation.value)
                            .background(Color(color))
                            .clickable { onSelectedItem(color) }
                    )
                }
            }
        }
    }
}