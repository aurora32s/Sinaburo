package com.haman.dearme.di

import com.haman.dearme.data.source.*
import com.haman.dearme.data.source.local.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindCategoryDataSource(
        categoryDataSourceImpl: CategoryDataSourceImpl
    ): CategoryDataSource

    @Binds
    abstract fun bindTimeDataSource(
        timeDataSourceImpl: TimeDataSourceImpl
    ): TimeDataSource

    @Binds
    abstract fun bindPlanDataSource(
        planDataSourceImpl: PlanDataSourceImpl
    ): PlanDataSource

    @Binds
    abstract fun bindDiaryDataSource(
        diaryDataSourceImpl: DiaryDataSourceImpl
    ): DiaryDataSource

    @Binds
    abstract fun bindPointDataSource(
        pointDataSourceImpl: PointDataSourceImpl
    ): PointDataSource

    @Binds
    abstract fun bindRateDataSource(
        rateDataSourceImpl: RateDataSourceImpl
    ): RateDataSource

    @Binds
    abstract fun bindChallengeDataSource(
        challengeDataSourceImpl: ChallengeDataSourceImpl
    ): ChallengeDataSource

    @Binds
    abstract fun bindScheduleDataSource(
        scheduleDataSourceImpl: ScheduleDataSourceImpl
    ): ScheduleDataSource
}