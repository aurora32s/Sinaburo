package com.haman.dearme.domain.repository

import com.haman.dearme.data.db.entity.CategoryEntity
import com.haman.dearme.ui.model.CategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    // 카테고리 추가
    suspend fun addCategory(categoryModel: CategoryModel): Result<Long>

    // 카테고리 제거
    suspend fun removeCategory(categoryModel: CategoryModel): Result<Int>

    // 모든 카테고리 리스트 요청
    fun getCategories(): Flow<List<CategoryEntity>>
}