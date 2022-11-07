package com.haman.dearme.data.source

import com.haman.dearme.data.db.entity.CategoryEntity
import com.haman.dearme.ui.model.CategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoryDataSource {
    // 카테고리 추가
    suspend fun insertCategory(categoryModel: CategoryModel): Result<Long>

    // 카테고리 제거
    suspend fun deleteCategory(categoryModel: CategoryModel): Result<Int>

    // 모든 카테고리 리스트 요청
    fun selectCategories(): Flow<List<CategoryEntity>>
}