package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.DiaryRepository
import com.haman.dearme.ui.model.DiaryModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RemoveDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(diaryId: Long): Result<Int> {
        return diaryRepository.removeDiary(diaryId)
    }
}