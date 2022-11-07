package com.haman.dearme.domain.repository

import com.haman.dearme.data.db.entity.DiaryEntity
import com.haman.dearme.data.db.entity.DiaryRateEntity
import com.haman.dearme.data.db.entity.RecordEntity
import com.haman.dearme.ui.model.DiaryModel
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    // 일기 요청
    suspend fun getDiary(year: Int, month: Int, day: Int): Result<RecordEntity>

    // 기록 추가
    suspend fun addDiary(diaryModel: DiaryModel): Result<Long>

    // 기록 변경
    suspend fun updateDiary(diaryModel: DiaryModel): Result<Int>

    // 기록 제거
    suspend fun removeDiary(diaryId: Long): Result<Int>

    // 월별 기록 요청
    suspend fun getMonthlyDiary(year: Int, month: Int): Result<DiaryEntity?>

    fun getAllDiary(): Flow<List<DiaryRateEntity>>
}