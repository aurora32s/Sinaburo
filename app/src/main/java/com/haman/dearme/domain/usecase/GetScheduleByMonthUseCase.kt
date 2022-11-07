package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.ScheduleEntity
import com.haman.dearme.domain.repository.ScheduleRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetScheduleByMonthUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(year: Int, month: Int): Result<List<ScheduleEntity>> {
        return scheduleRepository.getSchedule(year, month)
    }
}