package com.haman.dearme.domain.usecase

import com.haman.dearme.domain.repository.CategoryRepository
import com.haman.dearme.ui.model.CategoryModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryModel: CategoryModel): Result<Long> {
        return categoryRepository.addCategory(categoryModel)
    }
}