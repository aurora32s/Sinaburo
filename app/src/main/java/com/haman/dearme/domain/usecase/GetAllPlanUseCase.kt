package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.PlanEntity
import com.haman.dearme.data.db.entity.PlanMainEntity
import com.haman.dearme.domain.repository.PlanRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetAllPlanUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(year: Int, month: Int, day: Int): Result<List<PlanMainEntity>> {
        return planRepository.getPlan(year, month, day)
    }
}