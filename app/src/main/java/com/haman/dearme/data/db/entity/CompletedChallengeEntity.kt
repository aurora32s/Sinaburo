package com.haman.dearme.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.haman.dearme.ui.model.CompletedChallengeModel
import com.haman.dearme.util.ext.toLocalDate
import java.time.LocalDate

@Entity(
    tableName = "completed_challenge",
    foreignKeys = [
        ForeignKey(
            entity = ChallengeEntity::class,
            parentColumns = ["id"],
            childColumns = ["challenge_id"],
            onDelete = CASCADE
        )
    ]
)
data class CompletedChallengeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "challenge_id")
    val challengeId: Long,
    val date: Long
)

/**
 * 데이블 구조를 변경해야 할 때가 있었는데 Entity 가 Model 을 분리하니
 * toModel 만 변경하면 presentation Layer 쪽 코드는 수정할 필요가 없었습니다.
 */
fun CompletedChallengeEntity.toModel() = CompletedChallengeModel(
    challengeId = challengeId,
    date = date.toLocalDate()
)