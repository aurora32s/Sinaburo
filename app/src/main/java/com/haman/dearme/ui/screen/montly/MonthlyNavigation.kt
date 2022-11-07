package com.haman.dearme.ui.screen.montly

import androidx.annotation.DrawableRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.haman.dearme.R
import com.haman.dearme.ui.MainViewModel
import com.haman.dearme.ui.route.TopDestination
import com.haman.dearme.ui.screen.challenge.post.ChallengePostNavigation
import com.haman.dearme.ui.screen.diary.DiaryNavigation
import com.haman.dearme.ui.screen.montly.category.CategoryNavigation
import com.haman.dearme.ui.screen.montly.daily.DailyNavigation
import com.haman.dearme.ui.screen.montly.schedule.ScheduleNavigation

object MonthlyNavigation : TopDestination {
    override val route = "month"
    override val group = "month"
    @DrawableRes
    override val icon = R.drawable.monthly
    override val title = "Monthly"
}

fun NavGraphBuilder.month(
    mainViewModel: MainViewModel,
    navigate: (String, String) -> Unit
) {
    composable(route = MonthlyNavigation.route) {
        MonthlyScreen(
            toDailyScreen = { yearMonth ->
                navigate(DailyNavigation.route, "${yearMonth.year}/${yearMonth.month.value}")
            },
            toCategoryScreen = { yearMonth, category ->
                navigate(
                    CategoryNavigation.route,
                    "${yearMonth.year}/${yearMonth.month.value}/${category}"
                )
            },
            toChallengePostScreen = { challengeId ->
                navigate(
                    ChallengePostNavigation.route,
                    challengeId.toString()
                )
            },
            toSchedulePostScreen = { scheduleId ->
                navigate(
                    ScheduleNavigation.route,
                    scheduleId?.toString() ?: ""
                )
            },
            toScheduleAddScreen = { date ->
                navigate(
                    ScheduleNavigation.route,
                    "${date.year}/${date.month.value}/${date.dayOfMonth}"
                )
            },
            toDiaryScreen = { date ->
                mainViewModel.setDate(date.year, date.month.value, date.dayOfMonth)
                navigate(DiaryNavigation.route, "")
            }
        )
    }
}