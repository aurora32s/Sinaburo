package com.haman.dearme.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.haman.dearme.R

val fonts = FontFamily(
    Font(R.font.cookie_run)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fonts,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontSize = 14.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = fonts,
        fontSize = 12.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = fonts,
        fontSize = 10.sp
    )
)