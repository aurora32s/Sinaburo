package com.haman.dearme.ui.route

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.haman.dearme.R
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun BottomTabRow(
    allScreens: List<TopDestination>,
    onTabSelected: (TopDestination) -> Unit,
    currentScreen: Destination
) {
    Column(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(
                if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                else ColorPalette.White
            )
    ) {
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
        ) {
            allScreens.forEach { screen ->
                Tab(
                    title = screen.title,
                    icon = screen.icon,
                    onTabSelected = { onTabSelected(screen) },
                    selected = screen.group == currentScreen.group,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun Tab(
    title: String,
    icon: Int,
    onTabSelected: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(56.dp)
            .selectable(
                selected = selected,
                onClick = onTabSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false
                )
            )
            .clearAndSetSemantics { contentDescription = title },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconImage(
            modifier = Modifier.size(24.dp),
            icon = if (icon != 0) icon else R.drawable.close,
            alpha = if (selected) 1f else 0.5f,
            color = if (selected) {
                if (isSystemInDarkTheme()) ColorPalette.White
                else ColorPalette.ACCENT
            } else {
                ColorPalette.DARK_GREY
            }
        )
        if (selected) {
            Sub2(
                text = title,
                color = if (isSystemInDarkTheme()) ColorPalette.White
                else ColorPalette.ACCENT,
            )
        }
    }
}