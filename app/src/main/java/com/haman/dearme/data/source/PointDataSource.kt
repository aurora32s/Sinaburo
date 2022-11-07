package com.haman.dearme.data.source

import com.haman.dearme.data.db.entity.BadPointEntity
import com.haman.dearme.data.db.entity.GoodPointEntity
import com.haman.dearme.ui.model.BadPointModel
import com.haman.dearme.ui.model.GoodPointModel
import com.haman.dearme.ui.model.PointModel
import kotlinx.coroutines.flow.Flow

interface PointDataSource {
    // 잘한 점 추가
    suspend fun insertGoodPoint(pointModel: PointModel): Result<Long>

    // 못한 점 추가
    suspend fun insertBadPoint(pointModel: PointModel): Result<Long>

    // 잘한 점 제거
    suspend fun deleteGoodPoint(goodPointId: Long): Result<Int>

    // 못한 점 제거
    suspend fun deleteBadPoint(badPointId: Long): Result<Int>

    // 잘한 점 요청
    suspend fun selectGoodPoint(year: Int, month: Int, day: Int): Result<List<GoodPointEntity>>
    suspend fun selectGoodPoint(year: Int, month: Int): Result<List<GoodPointEntity>>
    fun selectGoodPoint(): Flow<List<GoodPointEntity>>

    // 못한 점 요청
    suspend fun selectBadPoint(year: Int, month: Int, day: Int): Result<List<BadPointEntity>>
    suspend fun selectBadPoint(year: Int, month: Int): Result<List<BadPointEntity>>
    fun selectBadPoint(): Flow<List<BadPointEntity>>
}