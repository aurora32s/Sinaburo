package com.haman.dearme.data.source

import com.haman.dearme.data.db.entity.*
import com.haman.dearme.ui.model.DetailModel
import com.haman.dearme.ui.model.plan.PlanModel

interface PlanDataSource {
    // 일정 추가 / 변경
    suspend fun insertPlan(
        plan: PlanModel,
        detailPlans: List<DetailModel>,
        ids: List<Long>
    ): Result<Long>

    // 일정 요청
    suspend fun getPlan(planId: Long): Result<PlanEntity>

    // 세부 일정 요청
    suspend fun selectDetailPlans(postId: Long): Result<List<DetailEntity>>

    // 특정 요일의 일정 요청
    suspend fun getAllPlans(year: Int, month: Int, day: Int): Result<List<PlanMainEntity>>

    // 세부 일저 완료 / 비완료 처리
    suspend fun updateDetailPlan(detailModel: DetailModel): Result<Int>

    // 특정 일정 제거
    suspend fun deletePlan(planId: Long): Result<Int>

    // 특정 일정 업데이트
    suspend fun updatePlan(planModel: PlanModel): Result<Int>

    // 완료한 일정 요청
    suspend fun getCompletedPlans(year: Int, month: Int, day: Int): Result<List<PlanDiaryEntity>>

    // 모든 일정 개수 요청
    suspend fun getAllPlanCnt(year: Int, month: Int, day: Int): Result<Int>

    // 특정 월의 일정 개수 요청
    suspend fun getMonthlyPlanCount(year: Int, month: Int): Result<List<MonthlyPlanEntity>>

    // 특정 월, 특정 카테고리의 개수 요청
    suspend fun getMonthlyPlanCountByCategory(
        year: Int,
        month: Int,
        category: Long
    ): Result<List<MonthlyPlanEntity>>

    // 특정 월의 카테고리별 일정 개수 요청
    suspend fun getMonthlyPlanByCategory(
        year: Int,
        month: Int
    ): Result<List<MonthlyPlanByCategoryEntity>>

    // 특정 월의 모든 일정 요청
    suspend fun getAllMonthlyPlan(year: Int, month: Int): Result<List<PlanDiaryEntity>>

    // 특정 월, 특정 카테고리의 모든 일정 요청
    suspend fun getAllMonthlyPlanByCategory(
        year: Int,
        month: Int,
        category: Long
    ): Result<List<PlanDiaryEntity>>
}