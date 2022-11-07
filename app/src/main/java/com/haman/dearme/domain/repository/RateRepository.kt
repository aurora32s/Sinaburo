package com.haman.dearme.domain.repository

import com.haman.dearme.data.db.entity.RateEntity
import com.haman.dearme.ui.model.RateModel

interface RateRepository {
    // rate 추가
    suspend fun addRate(rateModel: RateModel): Result<Long>

    // rate 요청
    suspend fun getRate(year: Int, month: Int, day: Int): Result<RateEntity?>
}