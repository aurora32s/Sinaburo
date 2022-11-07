package com.haman.dearme.data.source

import com.haman.dearme.data.db.entity.DiaryEntity
import com.haman.dearme.data.db.entity.DiaryRateEntity
import com.haman.dearme.ui.model.DiaryModel
import kotlinx.coroutines.flow.Flow

interface DiaryDataSource {
    // diary 추가
    suspend fun insertDiary(diaryModel: DiaryModel): Result<Long>

    // diary 요청
    suspend fun selectDairy(year: Int, month: Int, day: Int): Result<DiaryEntity?>

    // diary 업데이트
    suspend fun updateDiary(diaryModel: DiaryModel): Result<Int>

    // diary 제거
    suspend fun deleteDiary(diaryId: Long): Result<Int>

    fun selectAllDiary(): Flow<List<DiaryRateEntity>>
}