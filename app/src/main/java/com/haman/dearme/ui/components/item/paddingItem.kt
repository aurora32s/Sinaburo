package com.haman.dearme.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.theme.ColorPalette

fun LazyListScope.paddingItem(
    key: Any? = null,
    body: @Composable () -> Unit
) {
    item(key = key) {
        Surface(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            body()
        }
    }
}