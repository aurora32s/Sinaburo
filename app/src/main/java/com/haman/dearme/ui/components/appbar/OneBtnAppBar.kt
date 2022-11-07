package com.haman.dearme.ui.components.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.R
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.text.Body2

@Composable
fun OneBtnAppBar(
    title: String,
    leftIcon: @Composable () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leftIcon()
            Spacer(modifier = Modifier.width(8.dp))
            Body2(text = title)
            Spacer(modifier = Modifier.weight(1f))
        }
        Divider()
    }
}

@Composable
fun BackBtnAppBar(
    title: String,
    onBackPressed: () -> Unit
) {
    OneBtnAppBar(title = title) {
        IconImage(
            icon = R.drawable.plus,
            modifier = Modifier.clickable { onBackPressed() }
        )
    }
}