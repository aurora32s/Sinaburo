package com.haman.dearme.ui.components.item

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun LazyListScope.emptyDivider(
    height: Float = 1f
) {
    item {
        Spacer(modifier = Modifier.height(height.dp))
    }
}