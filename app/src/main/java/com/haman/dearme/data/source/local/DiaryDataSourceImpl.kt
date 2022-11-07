package com.haman.dearme.data.source.local

import com.haman.dearme.data.db.dao.DiaryDao
import com.haman.dearme.data.db.entity.DiaryEntity
import com.haman.dearme.data.db.entity.DiaryRateEntity
import com.haman.dearme.data.source.DiaryDataSource
import com.haman.dearme.ui.model.DiaryModel
import com.haman.dearme.ui.model.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiaryDataSourceImpl @Inject constructor(
    private val diaryDao: DiaryDao
) : DiaryDataSource {
    override suspend fun insertDiary(diaryModel: DiaryModel): Result<Long> = try {
        val result = if (diaryModel.id == null) {
            diaryDao.insertDiary(diaryModel.toEntity())
        } else {
            diaryDao.updateDiary(diaryModel.toEntity())
            diaryModel.id
        }

        if (result >= 0) Result.success(result)
        else throw Exception("일기를 추가하는 도중 문제가 발생하였습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectDairy(year: Int, month: Int, day: Int): Result<DiaryEntity?> = try {
        val result = diaryDao.selectDiary(year, month, day)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun updateDiary(diaryModel: DiaryModel): Result<Int> = try {
        val result = diaryDao.updateDiary(diaryModel.toEntity())

        if (result > 0) Result.success(result)
        else throw Exception("해당 기록이 존재하지 않습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun deleteDiary(diaryId: Long): Result<Int> = try {
        val result = diaryDao.deleteDiary(diaryId)

        if (result > 0) Result.success(result)
        else throw Exception("해당 기록이 존재하지 않습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override fun selectAllDiary(): Flow<List<DiaryRateEntity>> = diaryDao.selectAllDiary()
}