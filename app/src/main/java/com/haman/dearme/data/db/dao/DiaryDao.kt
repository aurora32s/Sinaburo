package com.haman.dearme.data.db.dao

import androidx.room.*
import com.haman.dearme.data.db.entity.DiaryEntity
import com.haman.dearme.data.db.entity.DiaryRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    // 특정 요일의 일기 저장
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiary(diaryEntity: DiaryEntity): Long

    // 특정 요일의 일기 요청
    @Query("SELECT * FROM diary WHERE year=:year and month=:month and day=:day")
    suspend fun selectDiary(year: Int, month: Int, day: Int): DiaryEntity?

    @Update
    suspend fun updateDiary(diaryEntity: DiaryEntity): Int

    @Query("DELETE FROM diary WHERE id = :diaryId")
    suspend fun deleteDiary(diaryId: Long): Int

    // 모든 일기 요청
    @Query(
        "select\n" +
                "d.id as id,\n" +
                "d.content as content,\n" +
                "r.rate as rate,\n" +
                "d.year as year,\n" +
                "d.month as month,\n" +
                "d.day as day\n" +
                "from diary d, rate r\n" +
                "where d.year = r.year\n" +
                "and d.month = r.month\n" +
                "and d.day = r.day " +
                "and d.day > 0 " +
                "order by year desc, month desc, day desc"
    )
    fun selectAllDiary(): Flow<List<DiaryRateEntity>>
}