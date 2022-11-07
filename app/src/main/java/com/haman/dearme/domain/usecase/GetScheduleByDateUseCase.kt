package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.ScheduleEntity
import com.haman.dearme.domain.repository.ScheduleRepository
import dagger.hilt.android.scopes.ViewModelScoped
import java.time.LocalDate
import javax.inject.Inject

@ViewModelScoped
class GetScheduleByDateUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(date: LocalDate): Result<List<ScheduleEntity>> {
        return scheduleRepository.getSchedule(date)
    }
}