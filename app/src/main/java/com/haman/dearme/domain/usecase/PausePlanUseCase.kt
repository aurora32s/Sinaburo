package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.PlanRepository
import com.haman.dearme.ui.model.plan.PlanModel
import com.haman.dearme.ui.model.plan.PlanState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class PausePlanUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planModel: PlanModel): Result<Int> {
        return planRepository.pauseAndStopPlan(planModel.copy(state = PlanState.PAUSE))
    }
}