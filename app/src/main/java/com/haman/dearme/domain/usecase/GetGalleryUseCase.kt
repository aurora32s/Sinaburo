package com.haman.dearme.domain.usecase

import android.net.Uri
import com.haman.dearme.domain.repository.GalleryRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class GetGalleryUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository
) {
    suspend operator fun invoke(year: Int, month: Int, day: Int): Result<List<Uri>> {
        return galleryRepository.getImage(year, month, day)
    }
}