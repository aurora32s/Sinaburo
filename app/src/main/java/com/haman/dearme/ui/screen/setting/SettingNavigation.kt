package com.haman.dearme.ui.screen.setting

import androidx.annotation.DrawableRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.haman.dearme.R
import com.haman.dearme.ui.route.TopDestination

object SettingNavigation: TopDestination {
    override val route = "setting"
    override val group = "setting"
    @DrawableRes
    override val icon = R.drawable.my
    override val title = "My"
}

fun NavGraphBuilder.setting() {
    composable(route = SettingNavigation.route) {
        SettingScreen()
    }
}