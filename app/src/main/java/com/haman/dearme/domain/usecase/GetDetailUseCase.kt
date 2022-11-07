package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.DetailEntity
import com.haman.dearme.domain.repository.PlanRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetDetailUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planId: Long): Result<List<DetailEntity>> {
        return planRepository.getDetailPlans(planId)
    }
}