package com.haman.dearme.ui.components.input

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun BaseInput(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean,
    textColor: Color = ColorPalette.Black,
    onValueChange: (String) -> Unit,
    number: Boolean,
    singleLine: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    placeholder: String = "입력해주세요."
) {
    Box(
        modifier = modifier.padding(4.dp)
    ) {
        BasicTextField(
            value = value,
            enabled = enabled,
            onValueChange = { result -> if (result.length <= maxLength) onValueChange(result) },
            textStyle = MaterialTheme.typography.subtitle1.copy(
                color = if (isSystemInDarkTheme()) ColorPalette.White
                else ColorPalette.DARK_BACKGROUND,
            ),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = if (number) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions.Default,
            singleLine = singleLine
        )

        if (value.isEmpty()) {
            Sub1(
                text = placeholder,
                color = ColorPalette.GREY.copy(alpha = if (isSystemInDarkTheme()) 0.2f else 1f)
            )
        }
    }
}