package com.haman.dearme.ui.model

import com.haman.dearme.data.db.entity.CategoryEntity

data class CategoryModel(
    val id: Long?,
    val name: String,
    val color: Long
)

fun CategoryModel.toEntity() = CategoryEntity(id = id, name = name, color = color)
