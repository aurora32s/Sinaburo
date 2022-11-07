package com.haman.dearme.ui.route

import com.haman.dearme.ui.screen.challenge.ChallengeNavigation
import com.haman.dearme.ui.screen.challenge.post.ChallengePostNavigation
import com.haman.dearme.ui.screen.diary.DiaryNavigation
import com.haman.dearme.ui.screen.history.HistoryNavigation
import com.haman.dearme.ui.screen.montly.MonthlyNavigation
import com.haman.dearme.ui.screen.montly.category.CategoryNavigation
import com.haman.dearme.ui.screen.montly.daily.DailyNavigation
import com.haman.dearme.ui.screen.montly.schedule.ScheduleNavigation
import com.haman.dearme.ui.screen.post.PostNavigation
import com.haman.dearme.ui.screen.setting.SettingNavigation

interface Destination {
    val route: String // 실제 route path
    val group: String // 속해 있는 group
}

interface TopDestination : Destination {
    val icon: Int // destination icon
    val title: String // destination title
}

val allScreens = listOf(
    HistoryNavigation,
    PostNavigation,
    DiaryNavigation,
    SettingNavigation,
    MonthlyNavigation,
    DailyNavigation,
    CategoryNavigation,
    ChallengeNavigation,
    ChallengePostNavigation,
    ScheduleNavigation
)

val bottomTabScreen = listOf(
    HistoryNavigation,
    DiaryNavigation,
    MonthlyNavigation,
    ChallengeNavigation,
    SettingNavigation
)