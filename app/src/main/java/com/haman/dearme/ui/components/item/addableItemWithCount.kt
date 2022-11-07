package com.haman.dearme.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.input.BaseInput
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.model.item.ItemWithCountHeader
import com.haman.dearme.ui.theme.ColorPalette

fun LazyListScope.addableItemWithCount(
    title: String,
    emptyMessage: String,
    isEmpty: Boolean,
    size: Int,
    onAddItem: (String) -> Unit,
    body: LazyListScope.() -> Unit
) {
    ItemWithCountHeader(
        title = title,
        emptyMessage = emptyMessage,
        isEmpty = isEmpty,
        size = size,
        body = body
    )
    emptyDivider(height = 8f)
    paddingItem {
        AddItem(onAddItem = onAddItem)
    }
}

@Composable
fun AddItem(
    modifier: Modifier = Modifier,
    onAddItem: (String) -> Unit
) {
    val title = rememberSaveable { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                color = ColorPalette.GREY,
                width = 1.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(
                if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                else ColorPalette.White
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BaseInput(
            value = title.value,
            enabled = true,
            onValueChange = { title.value = it },
            number = false,
            modifier = Modifier.weight(1f)
        )
        Sub1(
            text = "추가",
            modifier = Modifier.clickable {
                onAddItem(title.value)
                title.value = ""
            }
        )
    }
}