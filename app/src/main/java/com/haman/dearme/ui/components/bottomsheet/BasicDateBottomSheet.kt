package com.haman.dearme.ui.components.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.R
import com.haman.dearme.ui.theme.ColorPalette

/**
 * 날짜 선택 bottom sheet 의 container
 */
@Composable
fun BasicDateBottomSheet(
    modifier: Modifier = Modifier,
    onClickCloseBtn: () -> Unit,
    onClickConfirmBtn: () -> Unit,
    child: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Sub1(text = "날짜 선택")
            IconImage(
                icon = R.drawable.close,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onClickCloseBtn() },
                color = if (isSystemInDarkTheme()) ColorPalette.SECOND else ColorPalette.DARK_BACKGROUND
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            child()
        }
        Button(
            onClick = { onClickConfirmBtn() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorPalette.PRIMARY
            )
        ) {
            Sub1(text = "완료", color = ColorPalette.White)
        }
    }
}