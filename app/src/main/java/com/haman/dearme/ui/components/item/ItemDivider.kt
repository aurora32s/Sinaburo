package com.haman.dearme.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.theme.ColorPalette

fun LazyListScope.itemDivider(
    height: Float = 7f
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
                .background(
                    if (isSystemInDarkTheme())
                        ColorPalette.DARK_SPACER
                    else ColorPalette.SPACER
                )
        ) {}
    }
}