package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.MainChallengeEntity
import com.haman.dearme.domain.repository.ChallengeRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetAllChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    operator fun invoke(): Flow<List<MainChallengeEntity>> {
        return challengeRepository.getChallenge()
    }
}