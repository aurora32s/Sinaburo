package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.RateRepository
import com.haman.dearme.ui.model.RateModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddRateUseCase @Inject constructor(
    private val rateRepository: RateRepository
) {
    suspend operator fun invoke(rateModel: RateModel): Result<Long> {
        return rateRepository.addRate(rateModel)
    }
}