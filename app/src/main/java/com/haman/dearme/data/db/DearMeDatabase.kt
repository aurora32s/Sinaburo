package com.haman.dearme.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haman.dearme.data.db.dao.*
import com.haman.dearme.data.db.entity.*

@Database(
    entities = [
        CategoryEntity::class,
        DetailEntity::class,
        TimeEntity::class,
        PlanEntity::class,
        DiaryEntity::class,
        GoodPointEntity::class,
        BadPointEntity::class,
        RateEntity::class,
        ChallengeEntity::class,
        ChallengeDetailEntity::class,
        CompletedChallengeEntity::class,
        ScheduleEntity::class
    ],
    version = DearMeDatabase.DB_VERSION,
    exportSchema = false
)
abstract class DearMeDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun timeDao(): TimeDao
    abstract fun planDao(): PlanDao
    abstract fun diaryDao(): DiaryDao
    abstract fun pointDao(): PointDao
    abstract fun rateDao(): RateDao
    abstract fun challengeDao(): ChallengeDao
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "dear_me.db"
    }
}