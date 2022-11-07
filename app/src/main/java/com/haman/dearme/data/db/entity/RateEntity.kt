package com.haman.dearme.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.haman.dearme.ui.model.RateModel

@Entity(tableName = "rate")
data class RateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val rate: Int,
    val year: Int,
    val month: Int,
    val day: Int
)

fun RateEntity.toModel() = RateModel(
    id = id,
    rate = rate,
    year = year,
    month = month,
    day = day
)
