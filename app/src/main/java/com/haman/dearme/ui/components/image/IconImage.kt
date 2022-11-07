package com.haman.dearme.ui.components.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun IconImage(
    modifier: Modifier = Modifier,
    @DrawableRes
    icon: Int,
    color: Color? = null,
    alpha: Float = 1f
) {
    val colorFilter = if (color != null) ColorFilter.tint(color = color) else null
    Image(
        painter = painterResource(id = icon),
        contentDescription = null,
        colorFilter = colorFilter,
        modifier = modifier,
        alpha = alpha
    )
}