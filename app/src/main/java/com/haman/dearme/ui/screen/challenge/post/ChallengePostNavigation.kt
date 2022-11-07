package com.haman.dearme.ui.screen.challenge.post

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.haman.dearme.ui.route.Destination

object ChallengePostNavigation : Destination {
    override val route = "challenge_post"
    override val group = "challenge"

    const val challengeIdArgs = "challenge_id"
    val routeWithArgs = "${route}/{${challengeIdArgs}}"
    val arguments = listOf(
        navArgument(challengeIdArgs) { type = NavType.LongType }
    )
}

fun NavGraphBuilder.challengePost(
    onBackPressed: () -> Unit
) {
    composable(route = ChallengePostNavigation.route) {
        ChallengePostScreen(onBackPressed = onBackPressed)
    }
    composable(
        route = ChallengePostNavigation.routeWithArgs,
        arguments = ChallengePostNavigation.arguments
    ) {
        val challengeId = it.arguments?.getLong(ChallengePostNavigation.challengeIdArgs)
        ChallengePostScreen(
            challengeId = challengeId,
            onBackPressed = onBackPressed
        )
    }
}