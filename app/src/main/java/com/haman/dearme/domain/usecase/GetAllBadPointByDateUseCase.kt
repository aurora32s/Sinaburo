package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.BadPointEntity
import com.haman.dearme.domain.repository.PointRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetAllBadPointByDateUseCase @Inject constructor(
    private val pointRepository: PointRepository
) {
    suspend operator fun invoke(year: Int, month: Int): Result<List<BadPointEntity>> {
        return pointRepository.getAllBadPoint(year, month)
    }
}