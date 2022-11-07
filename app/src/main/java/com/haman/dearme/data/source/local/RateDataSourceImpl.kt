package com.haman.dearme.data.source.local

import com.haman.dearme.data.db.dao.RateDao
import com.haman.dearme.data.db.entity.RateEntity
import com.haman.dearme.data.source.RateDataSource
import com.haman.dearme.ui.model.RateModel
import com.haman.dearme.ui.model.toEntity
import javax.inject.Inject

class RateDataSourceImpl @Inject constructor(
    private val rateDao: RateDao
) : RateDataSource {
    override suspend fun selectRate(year: Int, month: Int, day: Int): Result<RateEntity?> = try {
        val result = rateDao.selectRate(year, month, day)
        Result.success(result)
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun insertRate(rateModel: RateModel): Result<Long> = try {
        val result = if (rateModel.id == null) {
            rateDao.insertRate(rateModel.toEntity())
        } else {
            rateDao.updateRate(rateModel.toEntity())
            rateModel.id
        }

        if (result >= 0) Result.success(result)
        else throw Exception("점수를 추가하는 도중 문제가 발생하였습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun updateRate(rateModel: RateModel): Result<Int> = try {
        val result = rateDao.updateRate(rateModel.toEntity())

        if (result > 0) Result.success(result)
        else throw Exception("해당 점수 정보를 찾지 못했습니다")
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}