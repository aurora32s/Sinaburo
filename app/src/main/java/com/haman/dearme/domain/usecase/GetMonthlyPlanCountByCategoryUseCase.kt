package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.MonthlyPlanEntity
import com.haman.dearme.domain.repository.PlanRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetMonthlyPlanCountByCategoryUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
        category: Long
    ): Result<List<MonthlyPlanEntity>> {
        return planRepository.getMonthlyPlanCountByCategory(year, month, category)
    }
}