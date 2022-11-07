package com.haman.dearme.data.source.local

import com.haman.dearme.data.db.dao.PlanDao
import com.haman.dearme.data.db.entity.*
import com.haman.dearme.data.source.PlanDataSource
import com.haman.dearme.ui.model.DetailModel
import com.haman.dearme.ui.model.plan.PlanModel
import com.haman.dearme.ui.model.plan.toEntity
import com.haman.dearme.ui.model.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlanDataSourceImpl @Inject constructor(
    private val planDao: PlanDao
) : PlanDataSource {
    override suspend fun insertPlan(
        plan: PlanModel,
        detailPlans: List<DetailModel>,
        ids: List<Long>
    ): Result<Long> = try {
        val result = planDao.insertPlan(plan.toEntity(), detailPlans, ids)

        if (result >= 0) Result.success(result)
        else throw Exception("일정을 추가/변경하는 도중 문제가 발생하였습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun getPlan(planId: Long): Result<PlanEntity> = try {
        val result = planDao.selectPlan(planId = planId)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun selectDetailPlans(postId: Long): Result<List<DetailEntity>> = try {
        val result = planDao.selectDetailPlans(postId)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun getAllPlans(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<PlanMainEntity>> =
        try {
            val result = planDao.selectAllPlans(year, month, day)
            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }

    override suspend fun updateDetailPlan(detailModel: DetailModel): Result<Int> = try {
        detailModel.planId ?: throw Exception("해당 세부 일정의 일정 id가 존재하지 않습니다.")
        val result = planDao.updateDetailPlan(detailModel.toEntity(detailModel.planId))

        if (result > 0) Result.success(result)
        else throw Exception("해당 상세 일정 정보가 존재하지 않습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun deletePlan(planId: Long): Result<Int> = try {
        val result = planDao.deletePlan(planId)

        if (result > 0) Result.success(result)
        else throw Exception("해당 일정 정보가 존재하지 않습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun updatePlan(planModel: PlanModel): Result<Int> = try {
        val result = planDao.updatePlan(planModel.toEntity())

        if (result > 0) Result.success(result)
        else throw Exception("해당 일정 정보가 존재하지 않습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun getCompletedPlans(
        year: Int,
        month: Int,
        day: Int
    ): Result<List<PlanDiaryEntity>> = try {
        val result = planDao.selectCompletedPlans(year, month, day)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun getAllPlanCnt(year: Int, month: Int, day: Int): Result<Int> = try {
        val result = planDao.selectPlanCount(year, month, day)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun getMonthlyPlanCount(
        year: Int,
        month: Int
    ): Result<List<MonthlyPlanEntity>> =
        try {
            val result = planDao.selectMonthlyPlan(year, month)
            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }

    override suspend fun getMonthlyPlanCountByCategory(
        year: Int,
        month: Int,
        category: Long
    ): Result<List<MonthlyPlanEntity>> = try {
        val result = planDao.selectMonthlyPlanCountByCategory(year, month, category)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun getMonthlyPlanByCategory(
        year: Int,
        month: Int
    ): Result<List<MonthlyPlanByCategoryEntity>> = try {
        val result = planDao.selectMonthlyPlanByCategory(year, month)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun getAllMonthlyPlan(year: Int, month: Int): Result<List<PlanDiaryEntity>> =
        try {
            val result = planDao.selectAllMonthlyPlan(year, month)
            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }

    override suspend fun getAllMonthlyPlanByCategory(
        year: Int,
        month: Int,
        category: Long
    ): Result<List<PlanDiaryEntity>> = try {
        val result = planDao.selectAllMonthlyPlanByCategory(year, month, category)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}