package com.haman.dearme.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun BaseClickableItem(
    modifier: Modifier = Modifier,
    message: String,
    arrangement: Arrangement.Horizontal = Arrangement.End,
    color: Color = if (isSystemInDarkTheme()) ColorPalette.DARK_GREY else ColorPalette.GREY,
    onClickItem: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(color)
            .clickable { onClickItem() }
            .padding(10.dp),
        horizontalArrangement = arrangement
    ) {
        Sub1(text = message)
    }
}