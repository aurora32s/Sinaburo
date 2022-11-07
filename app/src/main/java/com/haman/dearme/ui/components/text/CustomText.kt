package com.haman.dearme.ui.components.text

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.haman.dearme.ui.theme.fonts

const val FONT_WEIGHT_BOLD = 700
const val FONT_WEIGHT_LIGHT = 500

@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    align: TextAlign = TextAlign.Center,
    bold: Boolean,
    color: Color
) {
    Text(
        text = text,
        style = style,
        fontWeight = FontWeight(if (bold) FONT_WEIGHT_BOLD else FONT_WEIGHT_LIGHT),
        color = color,
        modifier = modifier.wrapContentHeight(Alignment.CenterVertically),
        textAlign = align
    )
}