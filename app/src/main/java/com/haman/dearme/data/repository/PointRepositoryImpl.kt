package com.haman.dearme.data.repository

import com.haman.dearme.data.db.entity.BadPointEntity
import com.haman.dearme.data.db.entity.GoodPointEntity
import com.haman.dearme.data.source.PointDataSource
import com.haman.dearme.di.IODispatcher
import com.haman.dearme.domain.repository.PointRepository
import com.haman.dearme.ui.model.PointModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val pointDataSource: PointDataSource
) : PointRepository {
    override suspend fun removeGoodPoint(pointId: Long): Result<Int> = withContext(ioDispatcher) {
        pointDataSource.deleteGoodPoint(pointId)
    }

    override suspend fun removeBadPoint(pointId: Long): Result<Int> = withContext(ioDispatcher) {
        pointDataSource.deleteBadPoint(pointId)
    }

    override suspend fun addGoodPoint(pointModel: PointModel): Result<Long> =
        withContext(ioDispatcher) {
            pointDataSource.insertGoodPoint(pointModel)
        }

    override suspend fun addBadPoint(pointModel: PointModel): Result<Long> =
        withContext(ioDispatcher) {
            pointDataSource.insertBadPoint(pointModel)
        }

    override suspend fun getAllGoodPoint(year: Int, month: Int): Result<List<GoodPointEntity>> =
        withContext(ioDispatcher) {
            pointDataSource.selectGoodPoint(year, month)
        }

    override fun getAllGoodPoint(): Flow<List<GoodPointEntity>> =
        pointDataSource.selectGoodPoint()

    override suspend fun getAllBadPoint(year: Int, month: Int): Result<List<BadPointEntity>> =
        withContext(ioDispatcher) {
            pointDataSource.selectBadPoint(year, month)
        }

    override fun getAllBadPoint(): Flow<List<BadPointEntity>> =
        pointDataSource.selectBadPoint()
}