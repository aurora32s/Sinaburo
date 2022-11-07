package com.haman.dearme.data.db.entity

import androidx.compose.ui.graphics.Color
import com.haman.dearme.ui.model.MainChallengeModel
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.toLocalDate
import kotlin.time.Duration.Companion.days

data class MainChallengeEntity(
    val id: Long,
    val name: String,
    val image: String?,
    val count: Int?,
    val startedAt: Long,
    val endedAt: Long,
    val categoryId: Long?,
    val categoryName: String?,
    val categoryColor: Long
)

fun MainChallengeEntity.toModel() = MainChallengeModel(
    id = id,
    name = name,
    image = image ?: "",
    count = count ?: 0,
    startedAt = startedAt.toLocalDate(),
    endedAt = endedAt.toLocalDate(),
    categoryId = categoryId ?: -1,
    categoryName = categoryName ?: "UnKnown",
    categoryColor = if (categoryColor == 0L) ColorPalette.DARK_GREY else Color(categoryColor)
)
