package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.PlanRepository
import com.haman.dearme.ui.model.DetailModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class CompleteDetailPlanUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(detailModel: DetailModel): Result<Int> {
        return planRepository.completeDetailPlan(detailModel)
    }
}