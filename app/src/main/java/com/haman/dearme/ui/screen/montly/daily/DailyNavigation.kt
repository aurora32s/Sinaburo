package com.haman.dearme.ui.screen.montly.daily

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.haman.dearme.ui.MainViewModel
import com.haman.dearme.ui.route.Destination
import com.haman.dearme.ui.screen.diary.DiaryNavigation
import com.haman.dearme.ui.screen.post.PostNavigation
import java.time.YearMonth

object DailyNavigation : Destination {
    override val route = "day"
    override val group = "month"

    const val yearArgs = "year"
    const val monthArgs = "month"
    val routeWithArgs = "${route}/{${yearArgs}}/{${monthArgs}}"
    val arguments = listOf(
        navArgument(yearArgs) { type = NavType.IntType },
        navArgument(monthArgs) { type = NavType.IntType }
    )
}

fun NavGraphBuilder.daily(
    navigate: (String, String) -> Unit,
    mainViewModel: MainViewModel,
    onBackPressed: () -> Unit
) {
    composable(
        route = DailyNavigation.routeWithArgs,
        arguments = DailyNavigation.arguments
    ) {
        val now = YearMonth.now()
        val year = it.arguments?.getInt(DailyNavigation.yearArgs) ?: now.year
        val month = it.arguments?.getInt(DailyNavigation.monthArgs) ?: now.month.value
        DailyScreen(
            year = year,
            month = month,
            onBackPressed = onBackPressed,
            toPostScreen = { postId -> navigate(PostNavigation.route, postId.toString()) },
            toDiaryScreen = { date ->
                mainViewModel.setDate(date.year, date.month.value, date.dayOfMonth)
                navigate(DiaryNavigation.route, "")
            }
        )
    }
}