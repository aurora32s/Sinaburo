package com.haman.dearme.ui.model

import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

data class MainChallengeModel(
    val id: Long,
    val name: String,
    val image: String,
    val count: Int,
    val startedAt: LocalDate,
    val endedAt: LocalDate,
    val categoryId: Long,
    val categoryName: String,
    val categoryColor: Color
) {
    val state = this.state()
}

fun MainChallengeModel.state(): ChallengeState {
    val now = LocalDate.now()
    val totalDate = ChronoUnit.DAYS.between(this.startedAt, this.endedAt) + 1
    val fromDate = ChronoUnit.DAYS.between(this.startedAt, now) + 1
    val successCnt = if (now < this.startedAt) 0 else this.count
    val failCnt =
        if (now < this.startedAt) 0 else if (now > this.endedAt) totalDate - successCnt else fromDate - successCnt
    return when {
        now < this.startedAt -> ChallengeState.TODO
        now > this.endedAt && failCnt > 0 -> ChallengeState.FAIL
        now > this.endedAt -> ChallengeState.SUCCESS
        else -> ChallengeState.DOING
    }
}