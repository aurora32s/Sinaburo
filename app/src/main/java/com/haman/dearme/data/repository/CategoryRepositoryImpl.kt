package com.haman.dearme.data.repository

import com.haman.dearme.data.db.entity.CategoryEntity
import com.haman.dearme.data.source.CategoryDataSource
import com.haman.dearme.di.IODispatcher
import com.haman.dearme.domain.repository.CategoryRepository
import com.haman.dearme.ui.model.CategoryModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val categoryDataSource: CategoryDataSource
) : CategoryRepository {
    override suspend fun addCategory(categoryModel: CategoryModel): Result<Long> =
        withContext(ioDispatcher) {
            categoryDataSource.insertCategory(categoryModel)
        }

    override suspend fun removeCategory(categoryModel: CategoryModel): Result<Int> =
        withContext(ioDispatcher) {
            categoryDataSource.deleteCategory(categoryModel)
        }

    override fun getCategories(): Flow<List<CategoryEntity>> = categoryDataSource.selectCategories()
}