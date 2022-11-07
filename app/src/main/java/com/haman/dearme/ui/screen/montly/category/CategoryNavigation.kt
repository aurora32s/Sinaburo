package com.haman.dearme.ui.screen.montly.category

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.haman.dearme.ui.MainViewModel
import com.haman.dearme.ui.route.Destination
import com.haman.dearme.ui.screen.diary.DiaryNavigation
import com.haman.dearme.ui.screen.post.PostNavigation
import java.time.YearMonth

object CategoryNavigation : Destination {
    override val route = "category"
    override val group = "month"

    const val yearArgs = "year"
    const val monthArgs = "month"
    const val category = "categoryId"

    val routeWithArgs = "${route}/{${yearArgs}}/{${monthArgs}}/{${category}}"
    val arguments = listOf(
        navArgument(yearArgs) { type = NavType.IntType },
        navArgument(monthArgs) { type = NavType.IntType },
        navArgument(category) { type = NavType.LongType }
    )
}

fun NavGraphBuilder.category(
    navigate: (String, String) -> Unit,
    mainViewModel: MainViewModel,
    onBackPressed: () -> Unit
) {
    composable(
        route = CategoryNavigation.routeWithArgs,
        arguments = CategoryNavigation.arguments
    ) {
        val now = YearMonth.now()

        val year = it.arguments?.getInt(CategoryNavigation.yearArgs) ?: now.year
        val month = it.arguments?.getInt(CategoryNavigation.monthArgs) ?: now.month.value
        val category = it.arguments?.getLong(CategoryNavigation.category) ?: -1

        CategoryScreen(
            year = year,
            month = month,
            category = category,
            onBackPressed = onBackPressed,
            toPostScreen = { postId -> navigate(PostNavigation.route, postId.toString()) },
            toDiaryScreen = { date ->
                mainViewModel.setDate(date.year, date.month.value, date.dayOfMonth)
                navigate(DiaryNavigation.route, "")
            }
        )
    }
}