package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.PointRepository
import com.haman.dearme.ui.model.PointModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddGoodPointUseCase @Inject constructor(
    private val pointRepository: PointRepository
) {
    suspend operator fun invoke(pointModel: PointModel): Result<Long> {
        return pointRepository.addGoodPoint(pointModel)
    }
}