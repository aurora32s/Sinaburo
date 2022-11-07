package com.haman.dearme.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.model.PointModel
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun PointItem(
    modifier: Modifier = Modifier,
    index: Int,
    point: PointModel,
    onRemovePoint: (Long) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(
                if (isSystemInDarkTheme()) ColorPalette.Black
                else ColorPalette.LIGHT_GREY
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Sub1(
            text = "${index + 1}. ${point.content}",
            color = if (isSystemInDarkTheme()) ColorPalette.White
            else ColorPalette.DARK_BACKGROUND,
            align = TextAlign.Start,
            modifier = Modifier.weight(9f)
        )
        Sub1(
            text = "삭제",
            color = if (isSystemInDarkTheme()) ColorPalette.White
            else ColorPalette.DARK_BACKGROUND,
            modifier = Modifier
                .weight(1f)
                .clickable { onRemovePoint(point.id!!) }
        )
    }
}