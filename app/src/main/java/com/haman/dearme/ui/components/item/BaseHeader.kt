package com.haman.dearme.ui.components.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.text.Body2
import com.haman.dearme.ui.theme.ColorPalette

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.itemWithBaseHeader(
    title: String,
    vararg body: (LazyListScope.() -> Unit)
) {
    itemDivider()
    stickyHeader { BaseHeader(title = title) }
    body.forEach {
        it()
        emptyDivider(height = 12f)
    }
}

@Composable
fun BaseHeader(
    title: String
) {
    Column(
        modifier = androidx.compose.ui.Modifier
            .background(if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND else ColorPalette.White)
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Row(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Body2(text = title)
        }
        BaseDivider()
    }
}