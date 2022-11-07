package com.haman.dearme.ui.screen.history

import androidx.annotation.DrawableRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.haman.dearme.R
import com.haman.dearme.ui.MainViewModel
import com.haman.dearme.ui.route.TopDestination
import com.haman.dearme.ui.screen.challenge.post.ChallengePostNavigation
import com.haman.dearme.ui.screen.montly.schedule.ScheduleNavigation
import com.haman.dearme.ui.screen.post.PostNavigation

object HistoryNavigation : TopDestination {
    override val route = "history"
    override val group = "history"
    @DrawableRes
    override val icon = R.drawable.main
    override val title = "Main"
}

fun NavGraphBuilder.history(
    mainViewModel: MainViewModel,
    navigate: (String, String) -> Unit
) {
    composable(route = HistoryNavigation.route) {
        HistoryScreen(
            mainViewModel = mainViewModel,
            toPostScreen = { postId -> navigate(PostNavigation.route, postId?.toString() ?: "") },
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
            },
            toPostScheduleScreen = { date ->
                navigate(
                    ScheduleNavigation.route,
                    "${date.year}/${date.month.value}/${date.dayOfMonth}"
                )
            }
        )
    }
}