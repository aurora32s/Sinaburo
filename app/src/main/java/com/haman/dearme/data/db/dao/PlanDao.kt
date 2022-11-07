package com.haman.dearme.data.db.dao

import androidx.room.*
import com.haman.dearme.data.db.entity.*
import com.haman.dearme.ui.model.DetailModel
import com.haman.dearme.ui.model.plan.PlanState
import com.haman.dearme.ui.model.toEntity

@Dao
interface PlanDao {
    // 특정 일정 추가 / 변경
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlan(plan: PlanEntity): Long

    // 세부 일정 추가 / 변경
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailPlans(detailPlans: List<DetailEntity>): List<Long>

    // 세부 일정 제거
    @Query("DELETE FROM detail WHERE id IN (:ids)")
    suspend fun deleteDetailPlans(ids: List<Long>): Int

    // 일정 추가
    @Transaction
    suspend fun insertPlan(
        plan: PlanEntity,
        detailPlans: List<DetailModel>,
        ids: List<Long>
    ): Long {
        // 1. 일정 추가 / 수정
        val planId = insertPlan(plan)
        // 2. 상세 일정 추가 / 수정
        insertDetailPlans(detailPlans.map { it.toEntity(planId) })
        // 3. 일정 삭제
        deleteDetailPlans(ids)

        return planId
    }

    // 일정 정보 요청
    @Query("SELECT * FROM `plan` WHERE id = :planId")
    suspend fun selectPlan(planId: Long): PlanEntity

    // 세부 일정 요청
    @Query("SELECT * FROM detail WHERE plan_id = :planId")
    suspend fun selectDetailPlans(planId: Long): List<DetailEntity>

    // 특정 요일의 모든 일정 요청
    @Query(
        "select \n" +
                "  p.id as id,\n" +
                "  p.title as title,\n" +
                "  p.state as state,\n" +
                "  c.name as categoryName,\n" +
                "  c.color as categoryColor\n" +
                "from `plan` p " +
                "left join category c " +
                "on p.category_id = c.id " +
                "where p.year = :year " +
                "and p.month = :month " +
                "and p.day = :day"
    )
    suspend fun selectAllPlans(year: Int, month: Int, day: Int): List<PlanMainEntity>

    // 특정 세부 일정 업데이트
    @Update
    suspend fun updateDetailPlan(detailEntity: DetailEntity): Int

    // 특정 일정 제거
    @Query("DELETE FROM `plan` WHERE id = :planId")
    suspend fun deletePlan(planId: Long): Int

    // 특정 일정 업데이트
    @Update
    suspend fun updatePlan(planEntity: PlanEntity): Int

    // 완료한 일정 요청
    // 특정 요일의 모든 일정 요청
    @Query(
        "select\n" +
                " p.id as id,\n" +
                " p.title as title,\n" +
                " p.state as state,\n" +
                " c.name as categoryName,\n" +
                " c.color as categoryColor,\n" +
                " t.duration as duration,\n" +
                " p.year as year,\n" +
                " p.month as month,\n" +
                " p.day as day\n" +
                "from `plan` p,\n" +
                "(\n" +
                " select plan_id, sum(endedAt - startedAt) as duration from time group by plan_id\n" +
                ") t " +
                "left join category c " +
                "on p.category_id = c.id \n" +
                "where p.id = t.plan_id\n" +
                "and p.year = :year \n" +
                "and p.month = :month \n" +
                "and p.day = :day \n" +
                "and p.state = :state"
    )
    suspend fun selectCompletedPlans(
        year: Int,
        month: Int,
        day: Int,
        state: Int = PlanState.STOP.ordinal
    ): List<PlanDiaryEntity>

    @Query("SELECT COUNT(*) FROM `plan` WHERE year=:year and month=:month and day=:day")
    suspend fun selectPlanCount(year: Int, month: Int, day: Int): Int

    @Query(
        "select\n" +
                "  day as date,\n" +
                "  count(case when state = 3 then 1 end) as completedPlan,\n" +
                "  count(case when state < 3 then 1 end) as noneCompletedPlan\n" +
                "from `plan` where year = :year and month = :month group by day"
    )
    suspend fun selectMonthlyPlan(year: Int, month: Int): List<MonthlyPlanEntity>

    @Query("select\n" +
            "p.day as date,\n" +
            "count(case when p.state = 3 then 1 end) as completedPlan,\n" +
            "count(case when state < 3 then 1 end) as noneCompletedPlan\n" +
            "from `plan` p\n" +
            "left join ( select id from category where id = :category ) c\n" +
            "on p.category_id = c.id\n" +
            "where p.year = :year and p.month = :month \n" +
            "group by p.day")
    suspend fun selectMonthlyPlanCountByCategory(
        year: Int,
        month: Int,
        category: Long
    ): List<MonthlyPlanEntity>

    @Query(
        "select\n" +
                "p.count as count,\n" +
                "t.time as time,\n" +
                "p.id as id,\n" +
                "p.categoryName as categoryName,\n" +
                "p.categoryColor as categoryColor\n" +
                "from (\n" +
                "   select\n" +
                "       count(*) as count," +
                "       p.id as planId,\n" +
                "       c.id as id,\n" +
                "       c.name as categoryName,\n" +
                "       c.color as categoryColor\n" +
                "   from `plan` p " +
                "   left join category c\n" +
                "   on p.category_id = c.id\n" +
                "   where p.year = :year and p.month = :month \n" +
                "   group by p.category_id\n" +
                ") p\n" +
                "left join (\n" +
                "   select\n" +
                "       plan_id,\n" +
                "       sum(time.endedAt - time.startedAt) as time\n" +
                "   from time\n" +
                "   group by plan_id\n" +
                ") t\n" +
                "on p.planId = t.plan_id " +
                "order by t.time desc"
    )
    suspend fun selectMonthlyPlanByCategory(
        year: Int,
        month: Int
    ): List<MonthlyPlanByCategoryEntity>

    @Query(
        "select\n" +
                "p.id,\n" +
                "p.title,\n" +
                "p.state,\n" +
                "p.categoryName,\n" +
                "p.categoryColor,\n" +
                "t.duration,\n" +
                "p.year,\n" +
                "p.month,\n" +
                "p.day\n" +
                "from (\n" +
                "   select\n" +
                "       p.id as id,\n" +
                "       p.title as title,\n" +
                "       p.state as state,\n" +
                "       c.name as categoryName,\n" +
                "       c.color as categoryColor,\n" +
                "       p.year as year,\n" +
                "       p.month as month,\n" +
                "       p.day as day\n" +
                "   from `plan` p " +
                "   left join category c\n" +
                "   on p.category_id = c.id\n" +
                "   where p.year = :year and p.month = :month\n" +
                ") p\n" +
                "left join (\n" +
                "   select\n" +
                "       plan_id,\n" +
                "       sum(endedAt - startedAt) as duration\n" +
                "   from time\n" +
                "   group by plan_id\n" +
                ") t\n" +
                "on p.id = t.plan_id\n" +
                "order by p.day desc, t.duration desc"
    )
    suspend fun selectAllMonthlyPlan(year: Int, month: Int): List<PlanDiaryEntity>

    @Query(
        "select\n" +
                "p.id,\n" +
                "p.title,\n" +
                "p.state,\n" +
                "p.categoryName,\n" +
                "p.categoryColor,\n" +
                "t.duration,\n" +
                "p.year,\n" +
                "p.month,\n" +
                "p.day\n" +
                "from (\n" +
                "select\n" +
                "p.id as id,\n" +
                "p.title as title,\n" +
                "p.state as state,\n" +
                "c.name as categoryName,\n" +
                "c.color as categoryColor,\n" +
                "p.year as year,\n" +
                "p.month as month,\n" +
                "p.day as day\n" +
                "from `plan` p " +
                "left join ( select * from category where id = :category) c \n" +
                "on p.category_id = c.id\n" +
                "where p.year = :year and p.month = :month \n" +
                ") p\n" +
                "left join (\n" +
                "select\n" +
                "plan_id,\n" +
                "sum(endedAt - startedAt) as duration\n" +
                "from time\n" +
                "group by plan_id\n" +
                ") t\n" +
                "on p.id = t.plan_id\n" +
                "order by t.duration desc"
    )
    suspend fun selectAllMonthlyPlanByCategory(
        year: Int,
        month: Int,
        category: Long
    ): List<PlanDiaryEntity>
}