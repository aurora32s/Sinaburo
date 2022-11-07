package com.haman.dearme.di

import android.content.Context
import com.haman.dearme.data.repository.*
import com.haman.dearme.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    abstract fun bindTimeRepository(
        timeRepositoryImpl: TimeRepositoryImpl
    ): TimeRepository

    @Binds
    abstract fun bindPlanRepository(
        planRepositoryImpl: PlanRepositoryImpl
    ): PlanRepository

    @Binds
    abstract fun bindDiaryRepository(
        diaryRepositoryImpl: DiaryRepositoryImpl
    ): DiaryRepository

    @Binds
    abstract fun bindPointRepository(
        pointRepositoryImpl: PointRepositoryImpl
    ): PointRepository

    @Binds
    abstract fun bindGalleryRepository(
        galleryRepositoryImpl: GalleryRepositoryImpl
    ): GalleryRepository

    @Binds
    abstract fun bindRateRepository(
        rateRepositoryImpl: RateRepositoryImpl
    ): RateRepository

    @Binds
    abstract fun bindChallengeRepository(
        challengeRepositoryImpl: ChallengeRepositoryImpl
    ): ChallengeRepository

    @Binds
    abstract fun bindScheduleRepository(
        scheduleRepositoryImpl: ScheduleRepositoryImpl
    ): ScheduleRepository
}