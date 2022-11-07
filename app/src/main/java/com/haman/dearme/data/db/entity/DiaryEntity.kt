package com.haman.dearme.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.haman.dearme.ui.model.DiaryModel

@Entity(tableName = "diary")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val content: String,
    val year: Int,
    val month: Int,
    val day: Int
)

fun DiaryEntity.toModel() = DiaryModel(
    id = id,
    content = content,
    year = year,
    month = month,
    day = day
)
