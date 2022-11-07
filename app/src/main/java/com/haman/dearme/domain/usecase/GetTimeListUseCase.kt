package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.TimeEntity
import com.haman.dearme.domain.repository.TimeRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetTimeListUseCase @Inject constructor(
    private val timeRepository: TimeRepository
) {
    operator fun invoke(planId: Long): Flow<List<TimeEntity>> {
        return timeRepository.getTimeById(planId)
    }
}