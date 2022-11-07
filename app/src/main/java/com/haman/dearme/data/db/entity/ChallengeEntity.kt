package com.haman.dearme.data.db.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey
import com.haman.dearme.ui.model.ChallengeModel
import com.haman.dearme.util.ext.toLocalDate
import java.time.Instant

@Entity(
    tableName = "challenge",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = SET_NULL
        )
    ]
)
data class ChallengeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val title: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Long?,
    val content: String,
    val startedAt: Long,
    val endedAt: Long,
    val image: String?
)

fun ChallengeEntity.toModel() = ChallengeModel(
    id = id,
    title = title,
    categoryId = categoryId,
    content = content,
    startedAt = startedAt.toLocalDate(),
    endedAt = endedAt.toLocalDate(),
    image = if (image != null) Uri.parse(image) else null
)