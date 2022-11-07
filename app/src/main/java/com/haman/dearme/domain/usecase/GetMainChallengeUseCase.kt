package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.MainChallengeEntity
import com.haman.dearme.data.db.entity.MainChallengeWithDetailEntity
import com.haman.dearme.domain.repository.ChallengeRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetMainChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<MainChallengeEntity>> {
        return challengeRepository.getChallenge(year, month, day)
    }
}