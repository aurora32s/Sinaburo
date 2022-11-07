package com.haman.dearme.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.haman.dearme.ui.model.ChallengeDetailModel
import com.haman.dearme.util.ext.toLocalDate
import java.time.LocalDate

@Entity(
    tableName = "challenge_detail",
    foreignKeys = [
        ForeignKey(
            entity = ChallengeEntity::class,
            parentColumns = ["id"],
            childColumns = ["challenge_id"],
            onDelete = CASCADE
        )
    ]
)
data class ChallengeDetailEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "challenge_id")
    val challengeId: Long,
    val title: String,
    val completed: Boolean = false,
    val date: Long?
)

fun ChallengeDetailEntity.toModel() = ChallengeDetailModel(
    id = id,
    challengeId = challengeId,
    title = title,
    completed = completed,
    date = if (completed) date?.toLocalDate() else null
)