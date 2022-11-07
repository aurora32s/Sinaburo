package com.haman.dearme.ui.model

import com.haman.dearme.data.db.entity.ChallengeDetailEntity
import com.haman.dearme.util.ext.getTime
import java.time.LocalDate

data class ChallengeDetailModel(
    val id: Long? = null,
    val challengeId: Long? = null,
    val title: String,
    val completed: Boolean = false,
    val date: LocalDate? = null
)

fun ChallengeDetailModel.toEntity(challengeId: Long) = ChallengeDetailEntity(
    id = id,
    challengeId = challengeId,
    title = title,
    completed = completed,
    date = date?.getTime()
)
