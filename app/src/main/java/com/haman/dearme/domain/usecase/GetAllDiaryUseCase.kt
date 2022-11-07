package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.DiaryRateEntity
import com.haman.dearme.domain.repository.DiaryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetAllDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    operator fun invoke(): Flow<List<DiaryRateEntity>> {
        return diaryRepository.getAllDiary()
    }
}