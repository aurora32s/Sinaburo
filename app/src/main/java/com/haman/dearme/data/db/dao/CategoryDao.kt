package com.haman.dearme.data.db.dao

import androidx.room.*
import com.haman.dearme.data.db.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    // 카테고리 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryEntity: CategoryEntity): Long

    // 카테고리 제거
    @Query("DELETE FROM category WHERE id = :categoryId")
    suspend fun deleteCategory(categoryId: Long): Int

    // 모든 카레고리 정보 요청
    @Query("SELECT * FROM category")
    fun selectCategories(): Flow<List<CategoryEntity>>
}