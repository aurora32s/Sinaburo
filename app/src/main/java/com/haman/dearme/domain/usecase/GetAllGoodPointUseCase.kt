package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.BadPointEntity
import com.haman.dearme.data.db.entity.GoodPointEntity
import com.haman.dearme.domain.repository.PointRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetAllGoodPointUseCase @Inject constructor(
    private val pointRepository: PointRepository
) {
    operator fun invoke(): Flow<List<GoodPointEntity>> {
        return pointRepository.getAllGoodPoint()
    }
}