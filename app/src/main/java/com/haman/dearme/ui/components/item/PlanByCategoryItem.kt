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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.chip.RectangleChip
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.model.MonthlyPlanByCategoryModel
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.fullTimeFormat

@Composable
fun PlanByCategoryItem(
    total: Float,
    category: MonthlyPlanByCategoryModel,
    onClickCategoryItem: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                else ColorPalette.White
            )
            .padding(vertical = 2.dp)
            .border(
                color = category.color,
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    bottomStart = 10.dp
                ),
                width = 1.dp
            )
            .clickable { onClickCategoryItem(category.categoryId) }
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RectangleChip(
            text = category.categoryName,
            color = category.color,
            modifier = Modifier.widthIn(min = 10.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Sub1(
            text = "${category.count} 개"
        )
        Spacer(modifier = Modifier.weight(1f))
        Sub1(text = category.value.fullTimeFormat())
        Spacer(modifier = Modifier.width(4.dp))
        Sub1(text = "${(category.value / total * 100).toInt()}%")
        Spacer(modifier = Modifier.width(4.dp))
        Sub1(text = ">")
    }
}