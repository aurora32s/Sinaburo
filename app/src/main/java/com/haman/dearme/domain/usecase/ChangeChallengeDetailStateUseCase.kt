package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.ChallengeRepository
import com.haman.dearme.ui.model.ChallengeDetailModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ChangeChallengeDetailStateUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(challengeDetailModel: ChallengeDetailModel): Result<Int> {
        return challengeRepository.changeChallengeDetailState(challengeDetailModel)
    }
}