package com.haman.dearme.ui.screen.diary

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.haman.dearme.R
import com.haman.dearme.ui.MainViewModel
import com.haman.dearme.ui.route.TopDestination
import com.haman.dearme.ui.screen.challenge.post.ChallengePostNavigation
import com.haman.dearme.ui.screen.montly.schedule.ScheduleNavigation
import com.haman.dearme.ui.screen.post.PostNavigation
import com.haman.dearme.ui.screen.post.post

object DiaryNavigation : TopDestination {
    override val route = "diary"
    override val group = "diary"
    @DrawableRes
    override val icon = R.drawable.daily
    override val title = "Daily"
}

fun NavGraphBuilder.diary(
    mainViewModel: MainViewModel,
    navigate: (String, String) -> Unit
) {
    composable(route = DiaryNavigation.route) {
        DiaryScreen(
            mainViewModel = mainViewModel,
            toPostScreen = { planId -> navigate(PostNavigation.route, planId.toString()) },
            toChallengePostScreen = { challengeId ->
                navigate(
                    ChallengePostNavigation.route,
                    challengeId.toString()
                )
            },
            toScheduleScreen = { scheduleId ->
                navigate(
                    ScheduleNavigation.route,
                    scheduleId?.toString() ?: ""
                )
            }
        )
    }
}