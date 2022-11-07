package com.haman.dearme.ui.model.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.common.EmptyListItem
import com.haman.dearme.ui.components.header.CountHeader
import com.haman.dearme.ui.components.item.itemDivider

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.ItemWithCountHeader(
    title: String,
    emptyMessage: String,
    isEmpty: Boolean,
    size: Int,
    header: @Composable () -> Unit = {},
    body: LazyListScope.() -> Unit
) {
    itemDivider()
    if (isEmpty) {
        item {
            CountHeader(title = title, count = size)
        }
        item {
            EmptyListItem(message = emptyMessage)
        }
    } else {
        stickyHeader {
            CountHeader(title = title, count = size)
        }
        item { header() }
        body()
    }
}
