package com.haman.dearme.ui.components.input

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun ContentInput(
    modifier: Modifier = Modifier,
    name: String,
    content: String,
    textColor: Color = ColorPalette.Black,
    number: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = false,
    maxLength: Int = 250,
    onValueChange: (String) -> Unit
) {
    InputField(
        modifier = modifier,
        name = name,
        textColor = textColor
    ) {
        Column(
        ) {
            Surface(
                color = Color.Transparent
            ) {
                BaseInput(
                    modifier = Modifier.fillMaxWidth(),
                    value = content,
                    enabled = enabled,
                    singleLine = singleLine,
                    maxLength = maxLength,
                    onValueChange = onValueChange,
                    number = number
                )
            }
            BaseDivider()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Sub2(text = "${content.length} / ${maxLength}")
            }
        }
    }
}