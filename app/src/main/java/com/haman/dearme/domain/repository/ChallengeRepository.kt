package com.haman.dearme.domain.repository

import com.haman.dearme.data.db.entity.*
import com.haman.dearme.ui.model.ChallengeDetailModel
import com.haman.dearme.ui.model.ChallengeModel
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    // 챌린지 추가 / 변경
    suspend fun insertChallenge(
        challenge: ChallengeModel,
        detailChallenge: List<ChallengeDetailModel>,
        ids: List<Long>
    ): Result<Long>

    // 특정 요일에 속하는 챌린지 요청
    suspend fun getChallenge(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<MainChallengeEntity>>

    fun getChallenge(): Flow<List<MainChallengeEntity>>

    suspend fun getChallengeById(id: Long): Result<ChallengeEntity>
    suspend fun getChallengeDetailById(id: Long): Result<List<ChallengeDetailEntity>>
    suspend fun changeChallengeDetailState(challengeDetailModel: ChallengeDetailModel): Result<Int>

    suspend fun addCompletedChallenge(
        challengeId: Long,
        year: Int,
        month: Int,
        day: Int
    ): Result<Long>

    suspend fun removeCompletedChallenge(
        challengeId: Long,
        year: Int,
        month: Int,
        day: Int
    ): Result<Int>

    suspend fun getCompletedChallenge(challengeId: Long): Result<List<CompletedChallengeEntity>>
    suspend fun getCompletedChallenge(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<CompletedChallengeEntity>>

    suspend fun getMonthlyChallenge(
        year: Int,
        month: Int
    ): Result<List<MainChallengeEntity>>

    suspend fun removeChallenge(challengeId: Long): Result<Int>
}