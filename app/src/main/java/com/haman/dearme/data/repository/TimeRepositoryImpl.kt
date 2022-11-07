package com.haman.dearme.data.repository

import com.haman.dearme.data.db.entity.TimeEntity
import com.haman.dearme.data.source.TimeDataSource
import com.haman.dearme.domain.repository.TimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimeRepositoryImpl @Inject constructor(
    private val timeDataSource: TimeDataSource
) : TimeRepository {
    override fun getTimeById(planId: Long): Flow<List<TimeEntity>> {
        return timeDataSource.selectTimes(planId)
    }
}