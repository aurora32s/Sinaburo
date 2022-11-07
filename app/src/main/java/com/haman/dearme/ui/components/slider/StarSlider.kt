package com.haman.dearme.ui.components.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.R
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun StarSlider(
    modifier: Modifier = Modifier,
    rate: Int,
    onClickRate: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                else ColorPalette.White
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        (1..5).forEach {
            if (it <= rate) {
                IconImage(
                    icon = R.drawable.app,
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { onClickRate(it) })
            } else {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                        .clickable { onClickRate(it) }
                        .border(
                            color = ColorPalette.GREY,
                            shape = RoundedCornerShape(999.dp),
                            width = 2.dp
                        )
                )
            }
        }
    }
}