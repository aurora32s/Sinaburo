package com.haman.dearme.data.source.local

import com.haman.dearme.data.db.dao.PointDao
import com.haman.dearme.data.db.entity.BadPointEntity
import com.haman.dearme.data.db.entity.GoodPointEntity
import com.haman.dearme.data.source.PointDataSource
import com.haman.dearme.ui.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PointDataSourceImpl @Inject constructor(
    private val pointDao: PointDao
) : PointDataSource {
    override suspend fun insertGoodPoint(pointModel: PointModel): Result<Long> = try {
        val result = pointDao.insertGoodPoint(pointModel.toGoodEntity())

        if (result >= 0) Result.success(result)
        else throw Exception("잘한 점을 추가하는 도중 문제가 발생하였습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun insertBadPoint(pointModel: PointModel): Result<Long> = try {
        val result = pointDao.insertBadPoint(pointModel.toBadEntity())
        if (result >= 0) Result.success(result)
        else throw Exception("잘못한 점을 추가하는 도중 문제가 발생하였습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun deleteGoodPoint(goodPointId: Long): Result<Int> = try {
        val result = pointDao.deleteGoodPoint(goodPointId)

        if (result > 0) Result.success(result)
        else throw Exception("해당 잘한 점을 찾지 못했습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun deleteBadPoint(badPointId: Long): Result<Int> = try {
        val result = pointDao.deleteBadPoint(badPointId)

        if (result > 0) Result.success(result)
        else throw Exception("해당 잘못한 점을 찾지 못했습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectGoodPoint(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<GoodPointEntity>> = try {
        val result = pointDao.selectGoodPoint(year, month, day)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectGoodPoint(
        year: Int,
        month: Int
    ): Result<List<GoodPointEntity>> = try {
        val result = pointDao.selectMonthlyGoodPoint(year, month)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override fun selectGoodPoint(): Flow<List<GoodPointEntity>> =
        pointDao.selectAllGoodPoint()

    override suspend fun selectBadPoint(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<BadPointEntity>> = try {
        val result = pointDao.selectBadPoint(year, month, day)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectBadPoint(
        year: Int,
        month: Int
    ): Result<List<BadPointEntity>> = try {
        val result = pointDao.selectMonthlyBadPoint(year, month)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override fun selectBadPoint(): Flow<List<BadPointEntity>> = pointDao.selectAllBadPoint()
}