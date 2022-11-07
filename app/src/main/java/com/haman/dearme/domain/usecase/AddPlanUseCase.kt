package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.PlanRepository
import com.haman.dearme.ui.model.DetailModel
import com.haman.dearme.ui.model.plan.PlanModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddPlanUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(
        plan: PlanModel,
        addedDetail: List<DetailModel>,
        deletedDetail: List<Long>
    ): Result<Long> {
        return planRepository.addPlanRepository(plan, addedDetail, deletedDetail)
    }
}