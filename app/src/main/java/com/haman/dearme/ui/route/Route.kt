package com.haman.dearme.ui.route

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.haman.dearme.ui.MainViewModel
import com.haman.dearme.ui.screen.challenge.challenge
import com.haman.dearme.ui.screen.challenge.post.challengePost
import com.haman.dearme.ui.screen.diary.diary
import com.haman.dearme.ui.screen.history.history
import com.haman.dearme.ui.screen.montly.category.category
import com.haman.dearme.ui.screen.montly.daily.daily
import com.haman.dearme.ui.screen.montly.month
import com.haman.dearme.ui.screen.montly.schedule.schedule
import com.haman.dearme.ui.screen.post.post
import com.haman.dearme.ui.screen.setting.setting
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun RouteHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        history(
            mainViewModel = mainViewModel,
            navigate = navController::push
        )
        post(
            mainViewModel = mainViewModel,
            onBackPressed = navController::popBackStack
        )
        diary(
            mainViewModel = mainViewModel,
            navigate = navController::push
        )
        month(mainViewModel = mainViewModel, navigate = navController::push)
        daily(
            navigate = navController::push,
            mainViewModel = mainViewModel,
            onBackPressed = navController::popBackStack
        )
        category(
            navigate = navController::push,
            mainViewModel = mainViewModel,
            onBackPressed = navController::popBackStack
        )
        schedule(
            onBackPressed = navController::popBackStack
        )
        challenge(
            navigate = navController::push
        )
        challengePost(
            onBackPressed = navController::popBackStack
        )
        setting()
    }
}

fun NavController.push(route: String, argument: String = "") {
    navigate("$route${if (argument.isBlank()) "" else "/${argument}"}") {
        launchSingleTop = true
    }
}