package com.haman.dearme.ui.components.item

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
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.chip.RectangleChip
import com.haman.dearme.ui.components.common.FullSpace
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.model.plan.PlanDiaryModel
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.fullTimeFormat

@Composable
fun DiaryPlanItem(
    modifier: Modifier = Modifier,
    plan: PlanDiaryModel,
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
        Sub1(text = plan.value.fullTimeFormat())
    }
}