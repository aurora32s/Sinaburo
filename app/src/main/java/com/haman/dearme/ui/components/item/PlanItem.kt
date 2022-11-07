package com.haman.dearme.ui.model.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.haman.dearme.R
import com.haman.dearme.ui.components.chip.RectangleChip
import com.haman.dearme.ui.components.common.FullSpace
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun PlanItem(
    modifier: Modifier = Modifier,
    plan: PlanMainModel,
    onClickPlanItem: (Long) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
            .border(
                width = (1.5).dp,
                color = if (isSystemInDarkTheme()) ColorPalette.White.copy(alpha = 0.2f)
                else ColorPalette.GREY,
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    bottomStart = 10.dp
                )
            )
            .clickable { onClickPlanItem(plan.id) }
            .background(
                if (isSystemInDarkTheme()) ColorPalette.Black
                else ColorPalette.White
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RectangleChip(
            text = plan.categoryName,
            color = plan.categoryColor
        )
        Spacer(modifier = Modifier.width(4.dp))
        Sub1(text = plan.title)
        FullSpace()
        if (plan.state != PlanState.INIT) {
            IconImage(
                modifier = Modifier.size(16.dp),
                icon = when (plan.state) {
                    PlanState.START -> R.drawable.play
                    PlanState.PAUSE -> R.drawable.pause
                    PlanState.STOP -> R.drawable.stop
                    else -> 0
                },
                color = plan.categoryColor
            )
        }
    }
}