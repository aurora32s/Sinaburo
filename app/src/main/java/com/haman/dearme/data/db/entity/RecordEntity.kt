package com.haman.dearme.data.db.entity

data class RecordEntity(
    val planDiary: List<PlanDiaryEntity>,
    val planCnt: Int,
    val diaryEntity: DiaryEntity?,
    val goods: List<GoodPointEntity>,
    val bads: List<BadPointEntity>,
    val rateEntity: RateEntity?,
    val challenges: List<MainChallengeEntity>
)
