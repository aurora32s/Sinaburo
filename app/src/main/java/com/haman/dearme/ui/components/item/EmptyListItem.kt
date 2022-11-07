package com.haman.dearme.ui.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.R
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.text.Sub1

@Composable
fun EmptyListItem(
    modifier: Modifier = Modifier,
    message: String,
    height: Float = 150f
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height((height).dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconImage(icon = R.drawable.empty)
            Sub1(text = message)
        }
    }
}