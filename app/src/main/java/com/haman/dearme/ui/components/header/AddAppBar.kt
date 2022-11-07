package com.haman.dearme.ui.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.text.Body1
import com.haman.dearme.R
import com.haman.dearme.ui.components.spinner.BaseDropDownSpinner
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun AddAppBar(
    mainTitle: String = "시나브로",
    options: List<String>? = null,
    onClickAddBtn: (Int) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .background(
                if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                else ColorPalette.White
            )
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Body1(text = mainTitle)
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.wrapContentSize(Alignment.TopEnd)
        ) {
            IconImage(
                icon = R.drawable.add,
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        if (options == null) onClickAddBtn(0) else expanded.value = true
                    },
                color = if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND
            )
            options?.let {
                BaseDropDownSpinner(
                    expanded = expanded.value,
                    onDismissSpinner = { expanded.value = false },
                    options = options,
                    onClickItem = onClickAddBtn
                )
            }
        }
    }
}