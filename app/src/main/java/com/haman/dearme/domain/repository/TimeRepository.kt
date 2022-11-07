package com.haman.dearme.domain.repository

import com.haman.dearme.data.db.entity.TimeEntity
import kotlinx.coroutines.flow.Flow

interface TimeRepository {
    // 특정 일정의 타임 리스트 요청
    fun getTimeById(planId: Long): Flow<List<TimeEntity>>
}