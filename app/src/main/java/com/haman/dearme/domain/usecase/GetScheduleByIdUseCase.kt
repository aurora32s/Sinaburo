package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.ScheduleEntity
import com.haman.dearme.domain.repository.ScheduleRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetScheduleByIdUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(scheduleId: Long): Result<ScheduleEntity> {
        return scheduleRepository.getSchedule(scheduleId = scheduleId)
    }
}