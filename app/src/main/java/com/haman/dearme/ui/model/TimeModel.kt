package com.haman.dearme.ui.model

import com.haman.dearme.data.db.entity.TimeEntity
import com.haman.dearme.util.ext.fullTimeFormat
import java.time.Instant
import java.time.ZoneId

data class TimeModel(
    val id: Long? = null,
    val planId: Long,
    val startedAt: Long,
    val endedAt: Long
)

fun TimeModel.toEntity() = TimeEntity(
    id = id,
    planId = planId,
    startedAt = startedAt,
    endedAt = endedAt
)

fun TimeModel.toDuration(): String {
    val startedAt =
        Instant.ofEpochMilli(this.startedAt).atZone(ZoneId.systemDefault()).toLocalDateTime()
            .fullTimeFormat()
    val endedAt =
        Instant.ofEpochMilli(this.endedAt).atZone(ZoneId.systemDefault()).toLocalDateTime()
            .fullTimeFormat()

    return "$startedAt ~ $endedAt"
}

fun List<TimeModel>.getDuration(): String {
    val duration = this.sumOf { it.endedAt - it.startedAt }
    return duration.fullTimeFormat()
}