package com.haman.dearme.data.source.local

import com.haman.dearme.data.db.dao.ChallengeDao
import com.haman.dearme.data.db.entity.*
import com.haman.dearme.data.source.ChallengeDataSource
import com.haman.dearme.ui.model.ChallengeDetailModel
import com.haman.dearme.ui.model.ChallengeModel
import com.haman.dearme.ui.model.MainChallengeModel
import com.haman.dearme.ui.model.toEntity
import com.haman.dearme.util.ext.getTime
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class ChallengeDataSourceImpl @Inject constructor(
    private val challengeDao: ChallengeDao
) : ChallengeDataSource {
    override suspend fun insertChallenge(
        challenge: ChallengeModel,
        detailChallenge: List<ChallengeDetailModel>,
        ids: List<Long>
    ): Result<Long> = try {
        val result = challengeDao.insertChallenge(
            challengeEntity = challenge.toEntity(),
            detailChallenge = detailChallenge,
            ids = ids
        )

        if (result >= 0) Result.success(result)
        else throw Exception("챌린지를 추가/변경하는 도중 문제가 발생하였습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectChallenge(date: Long): Result<List<MainChallengeEntity>> =
        try {
            val result = challengeDao.selectChallenge(date)
            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }

    override fun selectChallenge(): Flow<List<MainChallengeEntity>> = challengeDao.selectChallenge()

    override suspend fun selectChallengeById(id: Long): Result<ChallengeEntity> = try {
        val result = challengeDao.selectChallengeById(id)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectAllChallengeDetail(id: Long): Result<List<ChallengeDetailEntity>> =
        try {
            val result = challengeDao.selectChallengeDetail(id)
            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }

    override suspend fun updateChallengeDetail(challengeDetailModel: ChallengeDetailModel): Result<Int> =
        try {
            if (challengeDetailModel.challengeId == null) throw Exception("챌린지 정보가 존재하지 않는 상세 일정입니다.")
            val result = challengeDao.updateChallengeDetail(
                challengeDetailModel.toEntity(challengeDetailModel.challengeId!!)
            )

            if (result > 0) Result.success(result)
            else throw Exception("해당 상세 일정을 찾지 못했습니다.")
        } catch (exception: Exception) {
            Result.failure(exception)
        }

    override suspend fun insertCompletedChallenge(
        challengeId: Long,
        year: Int,
        month: Int,
        day: Int
    ): Result<Long> = try {
        val result = challengeDao.insertCompletedChallenge(
            CompletedChallengeEntity(
                id = null,
                challengeId = challengeId,
                date = LocalDate.of(year, month, day).getTime()
            )
        )
        if (result >= 0) Result.success(result)
        else throw Exception("챌린지를 완수하는 도중 문제가 발생하였습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun deleteCompletedChallenge(
        challengeId: Long,
        year: Int,
        month: Int,
        day: Int
    ): Result<Int> = try {
        val result =
            challengeDao.deleteCompletedChallenge(
                challengeId = challengeId,
                LocalDate.of(year, month, day).getTime()
            )

        if (result > 0) Result.success(result)
        else throw Exception("해당 챌린지가 존재하지 않습니다")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectCompletedChallenge(challengeId: Long): Result<List<CompletedChallengeEntity>> =
        try {
            val result = challengeDao.selectCompletedChallengeById(challengeId = challengeId)
            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }

    override suspend fun selectCompletedChallenge(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<CompletedChallengeEntity>> = try {
        val result =
            challengeDao.selectCompletedChallengeByDate(LocalDate.of(year, month, day).getTime())
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectDiaryChallenge(
        date: Long,
        year: Int,
        month: Int,
        day: Int
    ): Result<List<MainChallengeEntity>> = try {
        val result = challengeDao.selectDiaryChallenge(date)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectMonthlyChallenge(
        year: Int,
        month: Int
    ): Result<List<MainChallengeEntity>> = try {
        val date = YearMonth.of(year, month)
        val startedAt = date.atDay(1)
        val endedAt = date.atEndOfMonth()
        val result = challengeDao.selectMonthlyChallenge(
            startedAt.getTime(),
            endedAt.getTime()
        )
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun deleteChallenge(challengeId: Long): Result<Int> = try {
        val result = challengeDao.deleteChallenge(challengeId)

        if (result > 0) Result.success(result)
        else throw Exception("해당 챌린지를 찾지 못했습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}