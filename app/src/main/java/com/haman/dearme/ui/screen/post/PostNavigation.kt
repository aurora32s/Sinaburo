package com.haman.dearme.ui.screen.post

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.haman.dearme.ui.MainViewModel
import com.haman.dearme.ui.route.Destination

object PostNavigation : Destination {
    override val route = "post"
    override val group = "history"

    const val postIdArgs = "post_id"
    val routeWithArgs = "${route}/{${postIdArgs}}"
    val arguments = listOf(
        navArgument(postIdArgs) { type = NavType.LongType }
    )
}

fun NavGraphBuilder.post(
    mainViewModel: MainViewModel,
    onBackPressed: () -> Unit
) {
    composable(route = PostNavigation.route) {
        PostScreen(
            onBackPressed = onBackPressed,
            mainViewModel = mainViewModel
        )
    }
    composable(
        route = PostNavigation.routeWithArgs,
        arguments = PostNavigation.arguments
    ) {
        val postId = it.arguments?.getLong(PostNavigation.postIdArgs)
        PostScreen(
            postId = postId,
            onBackPressed = onBackPressed,
            mainViewModel = mainViewModel
        )
    }
}