package com.haman.dearme.data.repository

import com.haman.dearme.data.db.entity.*
import com.haman.dearme.data.source.ChallengeDataSource
import com.haman.dearme.di.IODispatcher
import com.haman.dearme.domain.repository.ChallengeRepository
import com.haman.dearme.ui.model.ChallengeDetailModel
import com.haman.dearme.ui.model.ChallengeModel
import com.haman.dearme.util.ext.getTime
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val challengeDataSource: ChallengeDataSource
) : ChallengeRepository {
    override suspend fun insertChallenge(
        challenge: ChallengeModel,
        detailChallenge: List<ChallengeDetailModel>,
        ids: List<Long>
    ): Result<Long> = withContext(ioDispatcher) {
        challengeDataSource.insertChallenge(
            challenge = challenge,
            detailChallenge = detailChallenge,
            ids = ids
        )
    }

    override suspend fun getChallenge(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<MainChallengeEntity>> = withContext(ioDispatcher) {
        challengeDataSource.selectChallenge(
            LocalDate.of(year, month, day).getTime()
        )
    }

    override fun getChallenge(): Flow<List<MainChallengeEntity>> =
        challengeDataSource.selectChallenge()

    override suspend fun getChallengeById(id: Long): Result<ChallengeEntity> =
        withContext(ioDispatcher) {
            challengeDataSource.selectChallengeById(id)
        }

    override suspend fun getChallengeDetailById(id: Long): Result<List<ChallengeDetailEntity>> =
        withContext(ioDispatcher) {
            challengeDataSource.selectAllChallengeDetail(id)
        }

    override suspend fun changeChallengeDetailState(challengeDetailModel: ChallengeDetailModel): Result<Int> =
        withContext(ioDispatcher) {
            challengeDataSource.updateChallengeDetail(challengeDetailModel)
        }

    override suspend fun addCompletedChallenge(
        challengeId: Long,
        year: Int,
        month: Int,
        day: Int
    ): Result<Long> =
        withContext(ioDispatcher) {
            challengeDataSource.insertCompletedChallenge(
                challengeId = challengeId,
                year = year,
                month = month,
                day = day
            )
        }

    override suspend fun removeCompletedChallenge(
        challengeId: Long,
        year: Int,
        month: Int,
        day: Int
    ): Result<Int> =
        withContext(ioDispatcher) {
            challengeDataSource.deleteCompletedChallenge(
                challengeId = challengeId,
                year = year,
                month = month,
                day = day
            )
        }

    override suspend fun getCompletedChallenge(challengeId: Long): Result<List<CompletedChallengeEntity>> =
        withContext(ioDispatcher) {
            challengeDataSource.selectCompletedChallenge(challengeId = challengeId)
        }

    override suspend fun getCompletedChallenge(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<CompletedChallengeEntity>> = withContext(ioDispatcher) {
        challengeDataSource.selectCompletedChallenge(year, month, day)
    }

    override suspend fun getMonthlyChallenge(
        year: Int,
        month: Int
    ): Result<List<MainChallengeEntity>> = withContext(ioDispatcher) {
        challengeDataSource.selectMonthlyChallenge(year, month)
    }

    override suspend fun removeChallenge(challengeId: Long): Result<Int> =
        withContext(ioDispatcher) {
            challengeDataSource.deleteChallenge(challengeId)
        }
}