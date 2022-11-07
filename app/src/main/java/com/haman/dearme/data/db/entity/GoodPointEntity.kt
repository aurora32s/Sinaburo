package com.haman.dearme.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.haman.dearme.ui.model.GoodPointModel
import com.haman.dearme.ui.model.PointModel

@Entity(tableName = "good_point")
data class GoodPointEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val content: String,
    val year: Int,
    val month: Int,
    val day: Int
)

fun GoodPointEntity.toModel() = PointModel(
    id = id,
    content = content,
    year = year,
    month = month,
    day = day
)