package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.PlanDiaryEntity
import com.haman.dearme.domain.repository.PlanRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetAllMonthlyPlanByCategoryUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
        category: Long
    ): Result<List<PlanDiaryEntity>> {
        return planRepository.getAllMonthlyPlanByCategory(year, month, category)
    }
}