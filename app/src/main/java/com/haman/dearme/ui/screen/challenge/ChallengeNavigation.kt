package com.haman.dearme.ui.screen.challenge

import androidx.annotation.DrawableRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.haman.dearme.R
import com.haman.dearme.ui.route.TopDestination
import com.haman.dearme.ui.screen.challenge.post.ChallengePostNavigation

object ChallengeNavigation : TopDestination {
    @DrawableRes
    override val icon = R.drawable.schedule
    override val title = "Challenge"
    override val route = "challenge"
    override val group = "challenge"
}

fun NavGraphBuilder.challenge(
    navigate: (String, String) -> Unit
) {
    composable(route = ChallengeNavigation.route) {
        ChallengeScreen(
            toPostScreen = { navigate(ChallengePostNavigation.route, it?.toString() ?: "") }
        )
    }
}