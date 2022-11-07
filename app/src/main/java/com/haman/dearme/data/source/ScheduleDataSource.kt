package com.haman.dearme.data.source

import com.haman.dearme.data.db.entity.ScheduleEntity
import com.haman.dearme.ui.model.ScheduleModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ScheduleDataSource {
    // 일정 추가
    suspend fun insertSchedule(schedule: ScheduleModel): Result<Long>

    // 일정 요청
    suspend fun selectSchedule(date: LocalDate): Result<List<ScheduleEntity>>
    suspend fun selectSchedule(year: Int, month: Int): Result<List<ScheduleEntity>>
    suspend fun selectSchedule(scheduleId: Long): Result<ScheduleEntity>

    // 모든 일정 요청
    fun selectSchedule(): Flow<List<ScheduleEntity>>

    suspend fun deleteSchedule(scheduleId: Long): Result<Int>
}