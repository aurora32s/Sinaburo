package com.haman.dearme.domain.repository

import android.net.Uri

interface GalleryRepository {
    // 갤러리 이미지 요청
    suspend fun getImage(year: Int, month: Int, day: Int): Result<List<Uri>>
}