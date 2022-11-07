package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.ChallengeRepository
import com.haman.dearme.ui.model.ChallengeDetailModel
import com.haman.dearme.ui.model.ChallengeModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(
        challenge: ChallengeModel,
        detailChallenge: List<ChallengeDetailModel>,
        ids: List<Long>
    ): Result<Long> {
        return challengeRepository.insertChallenge(challenge, detailChallenge, ids)
    }
}