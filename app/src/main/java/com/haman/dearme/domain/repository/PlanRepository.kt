package com.haman.dearme.domain.repository

import com.haman.dearme.data.db.entity.*
import com.haman.dearme.ui.model.DetailModel
import com.haman.dearme.ui.model.plan.PlanModel
import kotlinx.coroutines.flow.Flow

interface PlanRepository {
    // 일정 변경
    suspend fun addPlanRepository(
        plan: PlanModel,
        addedDetail: List<DetailModel>,
        deletedDetail: List<Long>
    ): Result<Long>

    // 일정 요청
    suspend fun getPlan(planId: Long): Result<PlanEntity>

    // 세부 일정 요청
    suspend fun getDetailPlans(planId: Long): Result<List<DetailEntity>>

    // 특정 요일의 일정 요청
    suspend fun getPlan(year: Int, month: Int, day: Int): Result<List<PlanMainEntity>>

    // 세부 일정 완료 / 비완료 처리
    suspend fun completeDetailPlan(detailModel: DetailModel): Result<Int>

    // 특정 일정 삭제
    suspend fun removePlan(planId: Long): Result<Int>

    // 특정 일정 시작
    suspend fun startPlan(planModel: PlanModel): Result<Int>

    // 특정 일정 일시정지 / 완료
    suspend fun pauseAndStopPlan(planModel: PlanModel): Result<Int>

    // 완료한 일정들 요청
    suspend fun getCompletedPlans(year: Int, month: Int, day: Int): Result<List<PlanDiaryEntity>>

    // 특정 월의 일정 요청
    suspend fun getMonthlyPlanCount(year: Int, month: Int): Result<List<MonthlyPlanEntity>>

    // 특정 월, 특정 카테고리의 일정 요청
    suspend fun getMonthlyPlanCountByCategory(
        year: Int,
        month: Int,
        category: Long
    ): Result<List<MonthlyPlanEntity>>

    // 특정 월의 카테고리별 일정 요청
    suspend fun getMonthlyPlanByCategory(
        year: Int,
        month: Int
    ): Result<List<MonthlyPlanByCategoryEntity>>

    // 특정 월의 모든 일정 요청
    suspend fun getAllMonthlyPlan(year: Int, month: Int): Result<List<PlanDiaryEntity>>

    // 특정 월, 특정 카테고리의 일정 요청
    suspend fun getAllMonthlyPlanByCategory(
        year: Int,
        month: Int,
        category: Long
    ): Result<List<PlanDiaryEntity>>
}