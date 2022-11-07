package com.haman.dearme.data.source.local

import com.haman.dearme.data.db.dao.PlanDao
import com.haman.dearme.data.db.dao.TimeDao
import com.haman.dearme.data.db.entity.TimeEntity
import com.haman.dearme.data.source.TimeDataSource
import com.haman.dearme.ui.model.TimeModel
import com.haman.dearme.ui.model.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimeDataSourceImpl @Inject constructor(
    private val timeDao: TimeDao,
    private val planDao: PlanDao
) : TimeDataSource {
    override suspend fun insertTime(planId: Long): Result<Long> = try {
        val plan = planDao.selectPlan(planId)
        val result = timeDao.insertTime(
            TimeEntity(
                planId = planId,
                startedAt = plan.startedAt,
                endedAt = System.currentTimeMillis()
            )
        )

        if (result >= 0) Result.success(result)
        else throw Exception("일정 상태를 변경하는 중 문제가 발생하였습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override fun selectTimes(planId: Long): Flow<List<TimeEntity>> = timeDao.selectTimes(planId)
}