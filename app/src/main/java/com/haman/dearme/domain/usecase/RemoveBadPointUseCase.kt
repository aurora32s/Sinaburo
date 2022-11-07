package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.PointRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RemoveBadPointUseCase @Inject constructor(
    private val pointRepository: PointRepository
) {
    suspend operator fun invoke(pointId: Long): Result<Int> {
        return pointRepository.removeBadPoint(pointId = pointId)
    }
}