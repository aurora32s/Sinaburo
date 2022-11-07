package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.PlanEntity
import com.haman.dearme.domain.repository.PlanRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetPlanUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planId: Long): Result<PlanEntity> {
        return planRepository.getPlan(planId)
    }
}