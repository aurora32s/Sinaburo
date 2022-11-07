package com.haman.dearme.data.repository

import com.haman.dearme.data.db.entity.RateEntity
import com.haman.dearme.data.source.RateDataSource
import com.haman.dearme.di.IODispatcher
import com.haman.dearme.domain.repository.RateRepository
import com.haman.dearme.ui.model.RateModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val rateDataSource: RateDataSource
) : RateRepository {
    override suspend fun addRate(rateModel: RateModel): Result<Long> = withContext(ioDispatcher) {
        rateDataSource.insertRate(rateModel)
    }

    override suspend fun getRate(year: Int, month: Int, day: Int): Result<RateEntity?> =
        withContext(ioDispatcher) {
            rateDataSource.selectRate(year, month, day)
        }
}