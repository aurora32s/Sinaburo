package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.ChallengeDetailEntity
import com.haman.dearme.domain.repository.ChallengeRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetChallengeDetailByIdUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(id: Long): Result<List<ChallengeDetailEntity>> {
        return challengeRepository.getChallengeDetailById(id)
    }
}