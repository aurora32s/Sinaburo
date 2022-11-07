package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.MainChallengeEntity
import com.haman.dearme.domain.repository.ChallengeRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetMonthlyChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(year: Int, month: Int): Result<List<MainChallengeEntity>> {
        return challengeRepository.getMonthlyChallenge(year, month)
    }
}