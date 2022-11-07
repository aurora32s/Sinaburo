package com.haman.dearme.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haman.dearme.data.db.entity.BadPointEntity
import com.haman.dearme.data.db.entity.GoodPointEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {
    // 좋은 점 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoodPoint(goodPointEntity: GoodPointEntity): Long

    // 잘못한 점 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBadPoint(badPointEntity: BadPointEntity): Long

    // 좋은 점 요청
    @Query("SELECT * FROM good_point WHERE year=:year and month=:month and day=:day")
    suspend fun selectGoodPoint(year: Int, month: Int, day: Int): List<GoodPointEntity>

    @Query("SELECT * FROM good_point WHERE year=:year and month=:month")
    suspend fun selectMonthlyGoodPoint(year: Int, month: Int): List<GoodPointEntity>

    // 잘못한 점 요청
    @Query("SELECT * FROM bad_point WHERE year=:year and month=:month and day=:day")
    suspend fun selectBadPoint(year: Int, month: Int, day: Int): List<BadPointEntity>

    @Query("SELECT * FROM bad_point WHERE year=:year and month=:month")
    suspend fun selectMonthlyBadPoint(year: Int, month: Int): List<BadPointEntity>

    // 좋은 점 제거
    @Query("DELETE FROM good_point WHERE id = :id")
    suspend fun deleteGoodPoint(id: Long): Int

    // 잘못한 점 제거
    @Query("DELETE FROM bad_point WHERE id = :id")
    suspend fun deleteBadPoint(id: Long): Int

    @Query("SELECT * FROM good_point")
    fun selectAllGoodPoint(): Flow<List<GoodPointEntity>>

    @Query("SELECT * FROM bad_point")
    fun selectAllBadPoint(): Flow<List<BadPointEntity>>
}