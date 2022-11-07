package com.haman.dearme.data.repository

import com.haman.dearme.data.db.dao.DiaryDao
import com.haman.dearme.data.db.dao.PlanDao
import com.haman.dearme.data.db.dao.PointDao
import com.haman.dearme.data.db.entity.DiaryEntity
import com.haman.dearme.data.db.entity.DiaryRateEntity
import com.haman.dearme.data.db.entity.RecordEntity
import com.haman.dearme.data.source.*
import com.haman.dearme.di.IODispatcher
import com.haman.dearme.domain.repository.DiaryRepository
import com.haman.dearme.ui.model.DiaryModel
import com.haman.dearme.util.ext.getTime
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val planDataSource: PlanDataSource,
    private val diaryDataSource: DiaryDataSource,
    private val pointDataSource: PointDataSource,
    private val rateDataSource: RateDataSource,
    private val challengeDataSource: ChallengeDataSource
) : DiaryRepository {
    override suspend fun getDiary(year: Int, month: Int, day: Int): Result<RecordEntity> =
        withContext(ioDispatcher) {
            val plan = planDataSource.getCompletedPlans(year, month, day)
            val planCnt = planDataSource.getAllPlanCnt(year, month, day)
            val diary = diaryDataSource.selectDairy(year, month, day)
            val goods = pointDataSource.selectGoodPoint(year, month, day)
            val bads = pointDataSource.selectBadPoint(year, month, day)
            val rate = rateDataSource.selectRate(year, month, day)
            val challenges =
                challengeDataSource.selectDiaryChallenge(
                    date = LocalDate.of(year, month, day).getTime(),
                    year = year,
                    month = month,
                    day = day
                )

            Result.success(
                RecordEntity(
                    planDiary = plan.getOrDefault(emptyList()),
                    planCnt = planCnt.getOrDefault(0),
                    diaryEntity = diary.getOrDefault(null),
                    goods = goods.getOrDefault(emptyList()),
                    bads = bads.getOrDefault(emptyList()),
                    rateEntity = rate.getOrDefault(null),
                    challenges = challenges.getOrDefault(emptyList())
                )
            )
        }

    override suspend fun addDiary(diaryModel: DiaryModel): Result<Long> =
        withContext(ioDispatcher) {
            diaryDataSource.insertDiary(diaryModel)
        }

    override suspend fun updateDiary(diaryModel: DiaryModel): Result<Int> =
        withContext(ioDispatcher) {
            diaryDataSource.updateDiary(diaryModel)
        }

    override suspend fun removeDiary(diaryId: Long): Result<Int> =
        withContext(ioDispatcher) {
            diaryDataSource.deleteDiary(diaryId)
        }

    override suspend fun getMonthlyDiary(year: Int, month: Int): Result<DiaryEntity?> =
        withContext(ioDispatcher) {
            diaryDataSource.selectDairy(year, month, -1)
        }

    override fun getAllDiary(): Flow<List<DiaryRateEntity>> = diaryDataSource.selectAllDiary()
}