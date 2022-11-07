package com.haman.dearme.ui.components.selector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.text.Body2
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.fullFormat
import java.time.YearMonth

@Composable
fun YearMonthSelector(
    modifier: Modifier = Modifier,
    date: YearMonth,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onClickMonth: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(999.dp),
        backgroundColor = ColorPalette.PRIMARY,
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Sub2(
                text = "이전달",
                modifier = Modifier
                    .weight(2f)
                    .clickable { onPrevMonth() },
                color = ColorPalette.White
            )
            Body2(
                text = date.fullFormat(),
                modifier = Modifier
                    .weight(6f)
                    .clickable { onClickMonth() },
                color = ColorPalette.White
            )
            Sub2(
                text = "다음달",
                modifier = Modifier
                .weight(2f)
                .clickable { onNextMonth() },
                color = ColorPalette.White
            )
        }
    }
}