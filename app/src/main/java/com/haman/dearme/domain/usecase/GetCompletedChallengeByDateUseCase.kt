package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.CompletedChallengeEntity
import com.haman.dearme.domain.repository.ChallengeRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetCompletedChallengeByDateUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<CompletedChallengeEntity>> {
        return challengeRepository.getCompletedChallenge(year, month, day)
    }
}