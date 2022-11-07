package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.CompletedChallengeEntity
import com.haman.dearme.domain.repository.ChallengeRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetCompletedChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: Long): Result<List<CompletedChallengeEntity>> {
        return challengeRepository.getCompletedChallenge(challengeId = challengeId)
    }
}