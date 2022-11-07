package com.haman.dearme.domain.repository

import com.haman.dearme.data.db.entity.ScheduleEntity
import com.haman.dearme.ui.model.ScheduleModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ScheduleRepository {
    // 일정 추가
    suspend fun addSchedule(schedule: ScheduleModel): Result<Long>

    // 일정 요청
    suspend fun getSchedule(date: LocalDate): Result<List<ScheduleEntity>>
    suspend fun getSchedule(year: Int, month: Int): Result<List<ScheduleEntity>>
    suspend fun getSchedule(scheduleId: Long): Result<ScheduleEntity>
    fun getSchedule(): Flow<List<ScheduleEntity>>

    suspend fun removeSchedule(scheduleId: Long): Result<Int>
}