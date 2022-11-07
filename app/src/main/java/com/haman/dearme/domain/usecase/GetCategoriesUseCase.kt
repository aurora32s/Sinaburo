package com.haman.dearme.domain.usecase

import com.haman.dearme.data.db.entity.CategoryEntity
import com.haman.dearme.domain.repository.CategoryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<List<CategoryEntity>> {
        return categoryRepository.getCategories()
    }
}