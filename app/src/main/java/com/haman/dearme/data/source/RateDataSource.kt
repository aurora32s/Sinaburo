package com.haman.dearme.data.source

import com.haman.dearme.data.db.entity.RateEntity
import com.haman.dearme.ui.model.RateModel

interface RateDataSource {
    // rate 요청
    suspend fun selectRate(year: Int, month: Int, day: Int): Result<RateEntity?>

    // rate 저장
    suspend fun insertRate(rateModel: RateModel): Result<Long>

    // rate 업데이트
    suspend fun updateRate(rateModel: RateModel): Result<Int>
}