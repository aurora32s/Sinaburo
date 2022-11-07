package com.haman.dearme.data.repository

import com.haman.dearme.data.db.entity.ScheduleEntity
import com.haman.dearme.data.source.ScheduleDataSource
import com.haman.dearme.di.IODispatcher
import com.haman.dearme.domain.repository.ScheduleRepository
import com.haman.dearme.ui.model.ScheduleModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val scheduleDataSource: ScheduleDataSource
) : ScheduleRepository {
    override suspend fun addSchedule(schedule: ScheduleModel): Result<Long> =
        withContext(ioDispatcher) {
            scheduleDataSource.insertSchedule(schedule)
        }

    override suspend fun getSchedule(date: LocalDate): Result<List<ScheduleEntity>> =
        withContext(ioDispatcher) {
            scheduleDataSource.selectSchedule(date)
        }

    override suspend fun getSchedule(year: Int, month: Int): Result<List<ScheduleEntity>> =
        withContext(ioDispatcher) {
            scheduleDataSource.selectSchedule(year, month)
        }

    override suspend fun getSchedule(scheduleId: Long): Result<ScheduleEntity> =
        withContext(ioDispatcher) {
            scheduleDataSource.selectSchedule(scheduleId)
        }

    override fun getSchedule(): Flow<List<ScheduleEntity>> = scheduleDataSource.selectSchedule()
    override suspend fun removeSchedule(scheduleId: Long): Result<Int> = withContext(ioDispatcher) {
        scheduleDataSource.deleteSchedule(scheduleId)
    }
}