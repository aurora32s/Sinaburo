package com.haman.dearme.ui.components.picker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun WheelTextPicker(
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(128.dp, 128.dp),
    startIndex: Int = 0,
    texts: List<String>,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1,
    textColor: Color = LocalContentColor.current,
    selectorEnabled: Boolean = true,
    selectorShape: Shape = RoundedCornerShape(16.dp),
    selectorColor: Color = ColorPalette.Primary,
    selectorBorder: BorderStroke? = BorderStroke(1.dp, ColorPalette.Primary),
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
) {
    WheelPicker(
        modifier = modifier,
        size = size,
        startIndex = startIndex,
        count = texts.size,
        selectorEnabled = selectorEnabled,
        selectorShape = selectorShape,
        selectorColor = selectorColor,
        selectorBorder = selectorBorder,
        onScrollFinished = onScrollFinished
    ) { index, snappedIndex ->
        Text(
            text = texts[index],
            style = textStyle,
            color = when (snappedIndex) {
                index -> {
                    textColor
                }
                else -> ColorPalette.LIGHT_GREY
            },
            maxLines = 1
        )
    }
}