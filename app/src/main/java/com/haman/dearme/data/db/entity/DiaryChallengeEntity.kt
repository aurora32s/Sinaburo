package com.haman.dearme.data.db.entity

import com.haman.dearme.ui.model.DiaryChallengeModel
import com.haman.dearme.util.ext.toLocalDate

data class DiaryChallengeEntity(
    val id: Long,
    val title: String,
    val image: String?,
    val startedAt: Long,
    val endedAt: Long,
    val categoryName: String,
    val categoryColor: Long
)

fun DiaryChallengeEntity.toModel() = DiaryChallengeModel(
    id = id,
    title = title,
    image = image ?: "",
    startedAt = startedAt.toLocalDate(),
    endedAt = endedAt.toLocalDate(),
    categoryName = categoryName,
    categoryColor = categoryColor
)