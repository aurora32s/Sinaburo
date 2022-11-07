package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.BadPointEntity
import com.haman.dearme.domain.repository.PointRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetAllBadPointUseCase @Inject constructor(
    private val pointRepository: PointRepository
){
    operator fun invoke(): Flow<List<BadPointEntity>> {
        return pointRepository.getAllBadPoint()
    }
}