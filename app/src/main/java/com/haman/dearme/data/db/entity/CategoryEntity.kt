package com.haman.dearme.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.haman.dearme.ui.model.CategoryModel

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?, // 카테고리 id
    val name: String, // 카테고리명
    val color: Long // 카테고리 색상
)

fun CategoryEntity.toModel() = CategoryModel(id = id, name = name, color = color)
