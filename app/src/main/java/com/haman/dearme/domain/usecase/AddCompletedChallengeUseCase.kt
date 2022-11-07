package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.ChallengeRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddCompletedChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: Long, year: Int, month: Int, day: Int): Result<Long> {
        return challengeRepository.addCompletedChallenge(
            challengeId = challengeId,
            year = year,
            month = month,
            day = day
        )
    }
}