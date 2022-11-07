package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.RecordEntity
import com.haman.dearme.domain.repository.DiaryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(year: Int, month: Int, day: Int): Result<RecordEntity> {
        return diaryRepository.getDiary(year, month, day)
    }
}