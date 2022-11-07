package com.haman.dearme.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.haman.dearme.ui.model.ScheduleModel
import com.haman.dearme.ui.model.ScheduleState
import com.haman.dearme.util.ext.toLocalDateTime

@Entity(
    tableName = "schedule"
)
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val title: String,
    val content: String,
    val location: String,
    val people: String,
    val startedAt: Long,
    val endedAt: Long,
    val important: Boolean,
    val state: Int
)

fun ScheduleEntity.toModel() = ScheduleModel(
    id = id,
    title = title,
    content = content,
    location = location,
    people = people,
    startedAt = startedAt.toLocalDateTime(),
    endedAt = endedAt.toLocalDateTime(),
    important = important,
    state = ScheduleState.getScheduleState(state)
)