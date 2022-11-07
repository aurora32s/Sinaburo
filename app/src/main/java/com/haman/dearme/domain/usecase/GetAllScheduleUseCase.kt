package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.ScheduleEntity
import com.haman.dearme.domain.repository.ScheduleRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetAllScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    operator fun invoke(): Flow<List<ScheduleEntity>> = scheduleRepository.getSchedule()
}