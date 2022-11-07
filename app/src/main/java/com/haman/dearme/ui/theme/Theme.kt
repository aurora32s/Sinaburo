package com.haman.dearme.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
//    primary = ColorPalette.PRIMARY,
//    primaryVariant = Primary,
//    secondary = Teal200,
//
//    onSurface = Color.White,
//    background = ColorPalette.DARK_BACKGROUND
    background = ColorPalette.DARK_BACKGROUND,
    surface = ColorPalette.DARK_BACKGROUND
)

private val LightColorPalette = lightColors(
    surface = ColorPalette.White
//    primary = ColorPalette.Primary,
//    primaryVariant = Primary,
//    secondary = Teal200,
//
//    onSurface = Color.Black
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DearMeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = ColorPalette.PRIMARY
    )
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}