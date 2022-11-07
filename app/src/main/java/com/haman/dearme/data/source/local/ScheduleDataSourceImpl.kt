package com.haman.dearme.data.source.local

import com.haman.dearme.data.db.dao.ScheduleDao
import com.haman.dearme.data.db.entity.ScheduleEntity
import com.haman.dearme.data.source.ScheduleDataSource
import com.haman.dearme.ui.model.ScheduleModel
import com.haman.dearme.ui.model.toEntity
import com.haman.dearme.util.ext.getTime
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import javax.inject.Inject

class ScheduleDataSourceImpl @Inject constructor(
    private val scheduleDao: ScheduleDao
) : ScheduleDataSource {
    override suspend fun insertSchedule(schedule: ScheduleModel): Result<Long> = try {
        val result = if (schedule.id == null) {
            scheduleDao.insertSchedule(schedule.toEntity())
        } else {
            scheduleDao.updateSchedule(schedule.toEntity())
            schedule.id
        }

        if (result >= 0) Result.success(result)
        else throw Exception("일정을 추가하는 도중 문제가 발생하였습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectSchedule(date: LocalDate): Result<List<ScheduleEntity>> = try {
        val result = scheduleDao.selectSchedule(
            startedAt = LocalDateTime.of(date, LocalTime.of(0, 0)).getTime(),
            endedAt = LocalDateTime.of(date.plusDays(1), LocalTime.of(0, 0)).getTime()
        )
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectSchedule(year: Int, month: Int): Result<List<ScheduleEntity>> = try {
        val yearMonth = YearMonth.of(year, month)
        val result = scheduleDao.selectSchedule(
            startedAt = LocalDateTime.of(yearMonth.atDay(1), LocalTime.of(0, 0))
                .getTime(),
            endedAt = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.of(23, 59)).getTime()
        )
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectSchedule(scheduleId: Long): Result<ScheduleEntity> = try {
        val result = scheduleDao.selectSchedule(scheduleId)

        if (result != null) Result.success(result)
        else throw Exception("해당하는 일정 정보가 존재하지 않습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override fun selectSchedule(): Flow<List<ScheduleEntity>> = scheduleDao.selectSchedule()
    override suspend fun deleteSchedule(scheduleId: Long): Result<Int> = try {
        val result = scheduleDao.deleteSchedule(scheduleId)

        if (result > 0) Result.success(result)
        else throw Exception("해당하는 일정을 찾지 못했습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}