package com.haman.dearme.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.haman.dearme.data.db.entity.TimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDao {
    // 타임 추가
    @Insert
    suspend fun insertTime(timeEntity: TimeEntity): Long

    // 타임 요청
    @Query("SELECT * FROM time WHERE plan_id = :planId")
    fun selectTimes(planId: Long): Flow<List<TimeEntity>>
}