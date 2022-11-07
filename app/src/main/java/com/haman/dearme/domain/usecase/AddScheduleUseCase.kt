package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.ScheduleRepository
import com.haman.dearme.ui.model.ScheduleModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(schedule: ScheduleModel): Result<Long> {
        return scheduleRepository.addSchedule(schedule)
    }
}