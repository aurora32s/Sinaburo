package com.haman.dearme.ui.components.spinner

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun BaseDropDownSpinner(
    expanded: Boolean,
    onDismissSpinner: () -> Unit,
    options: List<String>,
    onClickItem: (index: Int) -> Unit
) {
    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(5.dp))
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismissSpinner() },
            offset = DpOffset(x = 0.dp, y = 25.dp),
            modifier = Modifier
                .width(150.dp)
                .border(
                    width = 1.dp,
                    color = if (isSystemInDarkTheme()) ColorPalette.DARK_GREY else ColorPalette.LIGHT_GREY,
                    shape = RoundedCornerShape(5.dp)
                )
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(onClick = { onClickItem(index) }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Sub1(text = option)
                    }
                }
                if (index != options.size - 1) BaseDivider()
            }
        }
    }
}