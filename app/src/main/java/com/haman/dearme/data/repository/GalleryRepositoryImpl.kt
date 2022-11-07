package com.haman.dearme.data.repository

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import com.haman.dearme.di.IODispatcher
import com.haman.dearme.domain.repository.GalleryRepository
import com.haman.dearme.util.ext.getLastDate
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    @ApplicationContext
    val context: Context,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher
) : GalleryRepository {
    override suspend fun getImage(year: Int, month: Int, day: Int): Result<List<Uri>> =
        withContext(ioDispatcher) {
            try {
                val start = LocalDateTime.of(year, month, day, 0, 0)
                val end = LocalDateTime.of(year, month, day, 23, 59)
                val galleryPhotoList = mutableListOf<Uri>()
                val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

                val query: Cursor?
                val projection = arrayOf(
                    MediaStore.Images.ImageColumns.DISPLAY_NAME,
                    MediaStore.Images.ImageColumns.SIZE,
                    MediaStore.Images.ImageColumns.DATE_TAKEN,
                    MediaStore.Images.ImageColumns.DATE_ADDED,
                    MediaStore.Images.ImageColumns._ID
                )
                val resolver = context.contentResolver
                query = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    val bundle = Bundle().apply {
                        // selection
                        putString(
                            ContentResolver.QUERY_ARG_SQL_SELECTION,
                            "${MediaStore.Images.ImageColumns.DATE_TAKEN} >= ? and ${MediaStore.Images.ImageColumns.DATE_TAKEN} <= ?",
                        )
                        putStringArray(
                            ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS,
                            arrayOf(
                                "${
                                    start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                                }",
                                "${end.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()}"
                            )
                        )
                    }
                    resolver.query(uriExternal, projection, bundle, null)
                } else {
                    resolver.query(
                        uriExternal,
                        projection,
                        "${MediaStore.Images.ImageColumns.DATE_TAKEN} >= ? and ${MediaStore.Images.ImageColumns.DATE_TAKEN} <= ?",
                        arrayOf(
                            "${start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()}",
                            "${end.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()}"
                        ),
                        "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC"
                    )
                }
                query?.use { cursor ->
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                    val a = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)

                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(idColumn)
                        val tmpA = cursor.getLong(a)
                        print("$tmpA")
                        val contentUri = ContentUris.withAppendedId(uriExternal, id)

                        galleryPhotoList.add(contentUri)
                    }
                }

                Result.success(galleryPhotoList)
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }
}