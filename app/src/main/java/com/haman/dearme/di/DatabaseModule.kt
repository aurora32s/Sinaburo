package com.haman.dearme.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.haman.dearme.data.db.DearMeDatabase
import com.haman.dearme.data.db.migration.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ): DearMeDatabase = Room.databaseBuilder(
        context,
        DearMeDatabase::class.java,
        DearMeDatabase.DB_NAME
    ).addMigrations().build()
}

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Singleton
    @Provides
    fun provideCategoryDao(
        database: DearMeDatabase
    ) = database.categoryDao()

    @Singleton
    @Provides
    fun provideTimeDao(
        database: DearMeDatabase
    ) = database.timeDao()

    @Singleton
    @Provides
    fun providePlanDao(
        database: DearMeDatabase
    ) = database.planDao()

    @Singleton
    @Provides
    fun provideDiaryDao(
        database: DearMeDatabase
    ) = database.diaryDao()

    @Singleton
    @Provides
    fun providePointDao(
        database: DearMeDatabase
    ) = database.pointDao()

    @Singleton
    @Provides
    fun provideRateDao(
        database: DearMeDatabase
    ) = database.rateDao()

    @Singleton
    @Provides
    fun provideChallengeDao(
        database: DearMeDatabase
    ) = database.challengeDao()

    @Singleton
    @Provides
    fun provideScheduleDao(
        database: DearMeDatabase
    ) = database.scheduleDao()
}