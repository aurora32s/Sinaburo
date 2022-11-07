package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.DiaryEntity
import com.haman.dearme.domain.repository.DiaryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetMonthlyDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(year: Int, month: Int): Result<DiaryEntity?> {
        return diaryRepository.getMonthlyDiary(year, month)
    }
}