package com.haman.dearme.ui.model

import com.haman.dearme.data.db.entity.ScheduleEntity
import com.haman.dearme.util.ext.getTime
import java.time.LocalDateTime

data class ScheduleModel(
    val id: Long?,
    val title: String,
    val content: String,
    val location: String,
    val people: String,
    val startedAt: LocalDateTime,
    val endedAt: LocalDateTime,
    val important: Boolean,
    val state: ScheduleState
)

fun ScheduleModel.toEntity() = ScheduleEntity(
    id = id,
    title = title,
    content = content,
    location = location,
    people = people,
    startedAt = startedAt.getTime(),
    endedAt = endedAt.getTime(),
    important = important,
    state = state.ordinal
)