package com.haman.dearme.ui.components.item

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun <T> LazyListScope.paddingItems(
    items: List<T>,
    key: ((item: T) -> Any)? = null,
    body: @Composable (item: T) -> Unit
) {
    items(items = items, key = key) { item ->
        Surface(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            body(item)
        }
    }
}

fun <T> LazyListScope.paddingItemsIndexed(
    items: List<T>,
    key: ((index: Int, item: T) -> Any)? = null,
    body: @Composable (index: Int, item: T) -> Unit
) {
    itemsIndexed(items = items, key = key) { index, item ->
        Surface(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            body(index, item)
        }
    }
}