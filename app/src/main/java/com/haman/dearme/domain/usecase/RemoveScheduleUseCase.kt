package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.ScheduleRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RemoveScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(scheduleId: Long): Result<Int> {
        return scheduleRepository.removeSchedule(scheduleId)
    }
}