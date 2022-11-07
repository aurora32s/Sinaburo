package com.haman.dearme.ui.model

import android.net.Uri
import com.haman.dearme.data.db.entity.ChallengeEntity
import com.haman.dearme.util.ext.getTime
import java.time.LocalDate

data class ChallengeModel(
    val id: Long?,
    val title: String,
    val categoryId: Long?,
    val content: String,
    val startedAt: LocalDate,
    val endedAt: LocalDate,
    val image: Uri?
)

fun ChallengeModel.toEntity() = ChallengeEntity(
    id = id,
    title = title,
    categoryId = categoryId,
    content = content,
    startedAt = startedAt.getTime(),
    endedAt = endedAt.getTime(),
    image = image?.toString()
)