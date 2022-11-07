package com.haman.dearme.data.db.dao

import androidx.room.*
import com.haman.dearme.data.db.entity.RateEntity

@Dao
interface RateDao {
    // rate 저장
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(rateEntity: RateEntity): Long

    // rate 변경
    @Update
    suspend fun updateRate(rateEntity: RateEntity): Int

    // rate 요청
    @Query("SELECT * FROM rate WHERE year = :year and month = :month and day = :day")
    suspend fun selectRate(year: Int, month: Int, day: Int): RateEntity?
}