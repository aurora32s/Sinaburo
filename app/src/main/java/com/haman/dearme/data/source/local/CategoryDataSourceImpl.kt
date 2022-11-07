package com.haman.dearme.data.source.local

import com.haman.dearme.data.db.dao.CategoryDao
import com.haman.dearme.data.db.entity.CategoryEntity
import com.haman.dearme.data.source.CategoryDataSource
import com.haman.dearme.ui.model.CategoryModel
import com.haman.dearme.ui.model.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryDataSource {
    override suspend fun insertCategory(categoryModel: CategoryModel): Result<Long> = try {
        val result = categoryDao.insertCategory(categoryModel.toEntity())

        if (result >= 0) Result.success(result)
        else throw Exception("카테고리를 추가하지 못했습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun deleteCategory(categoryModel: CategoryModel): Result<Int> = try {
        println(categoryModel.id)
        categoryModel.id ?: throw Exception("카테고리의 id가 존재하지 않습니다.")
        val result = categoryDao.deleteCategory(categoryId = categoryModel.id)

        if (result > 0) Result.success(result)
        else throw Exception("해당 카테고리가 존재하지 않습니다.")
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override fun selectCategories(): Flow<List<CategoryEntity>> = categoryDao.selectCategories()
}