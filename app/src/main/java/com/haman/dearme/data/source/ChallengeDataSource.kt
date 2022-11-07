package com.haman.dearme.data.source

import com.haman.dearme.data.db.entity.*
import com.haman.dearme.ui.model.ChallengeDetailModel
import com.haman.dearme.ui.model.ChallengeModel
import kotlinx.coroutines.flow.Flow

interface ChallengeDataSource {
    // 챌린지 추가 / 변경
    suspend fun insertChallenge(
        challenge: ChallengeModel,
        detailChallenge: List<ChallengeDetailModel>,
        ids: List<Long>
    ): Result<Long>

    // 특정 요일에 속하는 챌린지 요청
    suspend fun selectChallenge(
        date: Long
    ): Result<List<MainChallengeEntity>>

    fun selectChallenge(): Flow<List<MainChallengeEntity>>

    suspend fun selectChallengeById(id: Long): Result<ChallengeEntity>
    suspend fun selectAllChallengeDetail(id: Long): Result<List<ChallengeDetailEntity>>

    suspend fun updateChallengeDetail(challengeDetailModel: ChallengeDetailModel): Result<Int>

    suspend fun insertCompletedChallenge(
        challengeId: Long,
        year: Int,
        month: Int,
        day: Int
    ): Result<Long>

    suspend fun deleteCompletedChallenge(
        challengeId: Long,
        year: Int,
        month: Int,
        day: Int
    ): Result<Int>

    suspend fun selectCompletedChallenge(challengeId: Long): Result<List<CompletedChallengeEntity>>
    suspend fun selectCompletedChallenge(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<CompletedChallengeEntity>>

    suspend fun selectDiaryChallenge(
        date: Long,
        year: Int,
        month: Int,
        day: Int
    ): Result<List<MainChallengeEntity>>

    suspend fun selectMonthlyChallenge(
        year: Int,
        month: Int
    ): Result<List<MainChallengeEntity>>

    suspend fun deleteChallenge(challengeId: Long): Result<Int>
}