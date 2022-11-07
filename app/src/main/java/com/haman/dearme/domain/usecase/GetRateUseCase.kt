package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.RateEntity
import com.haman.dearme.domain.repository.RateRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetRateUseCase @Inject constructor(
    private val rateRepository: RateRepository
) {
    suspend operator fun invoke(year: Int, month: Int, day: Int): Result<RateEntity?> {
        return rateRepository.getRate(year, month, day)
    }
}