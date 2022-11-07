package com.haman.dearme.data.repository

import com.haman.dearme.data.db.entity.*
import com.haman.dearme.data.source.PlanDataSource
import com.haman.dearme.data.source.TimeDataSource
import com.haman.dearme.di.IODispatcher
import com.haman.dearme.domain.repository.PlanRepository
import com.haman.dearme.ui.model.*
import com.haman.dearme.ui.model.plan.PlanModel
import com.haman.dearme.ui.model.plan.PlanState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val planDataSource: PlanDataSource,
    private val timeDataSource: TimeDataSource
) : PlanRepository {
    override suspend fun addPlanRepository(
        plan: PlanModel,
        addedDetail: List<DetailModel>,
        deletedDetail: List<Long>
    ): Result<Long> = withContext(ioDispatcher) {
        planDataSource.insertPlan(
            plan = plan,
            detailPlans = addedDetail,
            ids = deletedDetail
        )
    }

    override suspend fun getPlan(planId: Long): Result<PlanEntity> = withContext(ioDispatcher) {
        planDataSource.getPlan(planId)
    }

    override suspend fun getPlan(year: Int, month: Int, day: Int) = withContext(ioDispatcher) {
        planDataSource.getAllPlans(year, month, day)
    }

    override suspend fun getDetailPlans(planId: Long): Result<List<DetailEntity>> =
        withContext(ioDispatcher) {
            planDataSource.selectDetailPlans(planId)
        }

    override suspend fun completeDetailPlan(detailModel: DetailModel): Result<Int> =
        withContext(ioDispatcher) {
            planDataSource.updateDetailPlan(detailModel)
        }

    override suspend fun removePlan(planId: Long): Result<Int> = withContext(ioDispatcher) {
        planDataSource.deletePlan(planId)
    }

    override suspend fun startPlan(planModel: PlanModel): Result<Int> = withContext(ioDispatcher) {
        planDataSource.updatePlan(
            planModel.copy(
                startedAt = System.currentTimeMillis(),
                state = PlanState.START
            )
        )
    }

    override suspend fun pauseAndStopPlan(planModel: PlanModel): Result<Int> =
        withContext(ioDispatcher) {
            try {
                planModel.id?.let {
                    timeDataSource.insertTime(planModel.id)
                    planDataSource.updatePlan(planModel)
                } ?: throw Exception("id 가 존재하지 않는 데이터입니다.")
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }

    override suspend fun getCompletedPlans(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<PlanDiaryEntity>> = withContext(ioDispatcher) {
        planDataSource.getCompletedPlans(year, month, day)
    }

    override suspend fun getMonthlyPlanCount(
        year: Int,
        month: Int
    ): Result<List<MonthlyPlanEntity>> = withContext(ioDispatcher) {
        planDataSource.getMonthlyPlanCount(year, month)
    }

    override suspend fun getMonthlyPlanCountByCategory(
        year: Int,
        month: Int,
        category: Long
    ): Result<List<MonthlyPlanEntity>> = withContext(ioDispatcher) {
        planDataSource.getMonthlyPlanCountByCategory(year, month, category)
    }

    override suspend fun getMonthlyPlanByCategory(
        year: Int,
        month: Int
    ): Result<List<MonthlyPlanByCategoryEntity>> = withContext(ioDispatcher) {
        planDataSource.getMonthlyPlanByCategory(year, month)
    }

    override suspend fun getAllMonthlyPlan(year: Int, month: Int): Result<List<PlanDiaryEntity>> =
        withContext(ioDispatcher) {
            planDataSource.getAllMonthlyPlan(year, month)
        }

    override suspend fun getAllMonthlyPlanByCategory(
        year: Int,
        month: Int,
        category: Long
    ): Result<List<PlanDiaryEntity>> = withContext(ioDispatcher) {
        planDataSource.getAllMonthlyPlanByCategory(year, month, category)
    }
}