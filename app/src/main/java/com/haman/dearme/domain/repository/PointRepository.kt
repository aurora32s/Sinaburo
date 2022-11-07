package com.haman.dearme.domain.repository

import com.haman.dearme.data.db.entity.BadPointEntity
import com.haman.dearme.data.db.entity.GoodPointEntity
import com.haman.dearme.ui.model.PointModel
import kotlinx.coroutines.flow.Flow

interface PointRepository {
    // 좋은 점 제거
    suspend fun removeGoodPoint(pointId: Long): Result<Int>

    // 못한 점 제거
    suspend fun removeBadPoint(pointId: Long): Result<Int>

    // 좋은 점 추가
    suspend fun addGoodPoint(pointModel: PointModel): Result<Long>

    // 못한 점 추가
    suspend fun addBadPoint(pointModel: PointModel): Result<Long>

    // 잘한 점 요청
    suspend fun getAllGoodPoint(year: Int, month: Int): Result<List<GoodPointEntity>>
    fun getAllGoodPoint(): Flow<List<GoodPointEntity>>

    // 못한 점 요청
    suspend fun getAllBadPoint(year: Int, month: Int): Result<List<BadPointEntity>>
    fun getAllBadPoint(): Flow<List<BadPointEntity>>
}