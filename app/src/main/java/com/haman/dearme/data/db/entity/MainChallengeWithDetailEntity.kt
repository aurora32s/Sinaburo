package com.haman.dearme.data.db.entity

import com.haman.dearme.ui.model.MainChallengeModel

data class MainChallengeWithDetailEntity(
    val mainChallengeEntity: MainChallengeEntity,
    val details: List<ChallengeDetailEntity>
)