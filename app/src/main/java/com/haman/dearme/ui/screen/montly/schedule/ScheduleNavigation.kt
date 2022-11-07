package com.haman.dearme.ui.screen.montly.schedule

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.haman.dearme.ui.route.Destination
import java.time.LocalDate

object ScheduleNavigation : Destination {
    override val route = "schedule"
    override val group = "month"

    const val scheduleIdArgs = "schedule_id"
    const val yearArgs = "year"
    const val monthArgs = "month"
    const val dateArgs = "date"
    val routeWithArgs = "${route}/{${scheduleIdArgs}}"
    val routeWithDate = "${route}/{${yearArgs}}/{${monthArgs}}/{${dateArgs}}"
    val arguments = listOf(
        navArgument(scheduleIdArgs) { type = NavType.LongType }
    )
    val dateArguments = listOf(
        navArgument(yearArgs) { type = NavType.IntType },
        navArgument(monthArgs) { type = NavType.IntType },
        navArgument(dateArgs) { type = NavType.IntType }
    )
}

fun NavGraphBuilder.schedule(
    onBackPressed: () -> Unit
) {
    composable(
        route = ScheduleNavigation.routeWithArgs,
        arguments = ScheduleNavigation.arguments
    ) {
        val scheduleId = it.arguments?.getLong(ScheduleNavigation.scheduleIdArgs)
        ScheduleScreen(
            scheduleId = scheduleId,
            onBackPressed = onBackPressed
        )
    }
    composable(
        route = ScheduleNavigation.routeWithDate,
        arguments = ScheduleNavigation.dateArguments
    ) {
        val year = it.arguments?.getInt(ScheduleNavigation.yearArgs)
        val month = it.arguments?.getInt(ScheduleNavigation.monthArgs)
        val day = it.arguments?.getInt(ScheduleNavigation.dateArgs)

        if (year != null && month != null && day != null) {
            ScheduleScreen(
                date = LocalDate.of(year, month, day),
                onBackPressed = onBackPressed
            )
        }
    }
}