package com.haman.dearme.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.haman.dearme.data.db.entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Insert
    suspend fun insertSchedule(schedule: ScheduleEntity): Long

    @Update
    suspend fun updateSchedule(schedule: ScheduleEntity): Int

    @Query(
        "SELECT * FROM schedule WHERE startedAt < :endedAt and endedAt >= :startedAt"
    )
    suspend fun selectSchedule(startedAt: Long, endedAt: Long): List<ScheduleEntity>

    @Query("SELECT * FROM schedule WHERE id = :scheduleId")
    suspend fun selectSchedule(scheduleId: Long): ScheduleEntity?

    @Query("SELECT * FROM schedule ORDER BY startedAt")
    fun selectSchedule(): Flow<List<ScheduleEntity>>

    @Query("DELETE FROM schedule WHERE id = :scheduleId")
    suspend fun deleteSchedule(scheduleId: Long): Int

}