package com.haman.dearme.data.source

import com.haman.dearme.data.db.entity.TimeEntity
import com.haman.dearme.ui.model.TimeModel
import kotlinx.coroutines.flow.Flow

interface TimeDataSource {
    // 타임 저장
    suspend fun insertTime(planId: Long): Result<Long>

    // 특정 plan 의 타임 요청
    fun selectTimes(planId: Long): Flow<List<TimeEntity>>
}