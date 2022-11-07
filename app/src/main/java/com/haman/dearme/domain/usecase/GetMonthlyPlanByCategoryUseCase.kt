package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.MonthlyPlanByCategoryEntity
import com.haman.dearme.domain.repository.PlanRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetMonthlyPlanByCategoryUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(year: Int, month: Int): Result<List<MonthlyPlanByCategoryEntity>> {
        return planRepository.getMonthlyPlanByCategory(year, month)
    }
}