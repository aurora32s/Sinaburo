package com.haman.dearme.ui.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.text.Body2
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun CountHeader(
    title: String,
    count: Int
) {
    Column(
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND else ColorPalette.White)
            .padding(vertical = 2.dp, horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Body2(text = title)
            Sub1(text = "${count}개")
        }
        BaseDivider()
    }
}