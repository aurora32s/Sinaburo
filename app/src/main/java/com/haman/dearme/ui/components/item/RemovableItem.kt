package com.haman.dearme.ui.model.item

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.haman.dearme.R
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.theme.ColorPalette

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RemovableItem(
    item: @Composable () -> Unit,
    dismissColor: Color,
    onRemoveItem: () -> Unit,
    direction: Set<DismissDirection> = setOf(DismissDirection.EndToStart),
    directionThreshold: Float = 0.25f
) {
    val dismissState = rememberDismissState(DismissValue.Default)

    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        onRemoveItem()
    }

    SwipeToDismiss(
        state = dismissState,
        directions = direction,
        dismissThresholds = { FractionalThreshold(directionThreshold) },
        background = {
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    DismissValue.Default -> if (isSystemInDarkTheme()) ColorPalette.DARK_GREY else ColorPalette.GREY
                    else -> dismissColor
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 2.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 10.dp,
                            bottomStart = 10.dp,
                            topEnd = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconImage(icon = R.drawable.delete)
            }
        },
        dismissContent = { item() }
    )
}