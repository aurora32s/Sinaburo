package com.haman.dearme.data.db.dao

import androidx.room.*
import com.haman.dearme.data.db.entity.*
import com.haman.dearme.ui.model.ChallengeDetailModel
import com.haman.dearme.ui.model.toEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {
    // 특정 일정 추가
    @Insert
    suspend fun insertChallenge(challengeEntity: ChallengeEntity): Long

    @Insert
    suspend fun insertCompletedChallenge(completedChallengeEntity: CompletedChallengeEntity): Long

    @Query(
        "DELETE FROM completed_challenge " +
                "WHERE challenge_id = :challengeId " +
                "and date = :date"
    )
    suspend fun deleteCompletedChallenge(challengeId: Long, date: Long): Int

    @Query("SELECT * FROM completed_challenge WHERE challenge_id = :challengeId")
    suspend fun selectCompletedChallengeById(challengeId: Long): List<CompletedChallengeEntity>

    @Query(
        "SELECT * FROM completed_challenge WHERE date = :date"
    )
    suspend fun selectCompletedChallengeByDate(date: Long): List<CompletedChallengeEntity>

    // 특정 일정 업데이트
    @Update
    suspend fun updateChallenge(challengeEntity: ChallengeEntity): Int

    // 세부 일정 제거
    @Query("DELETE FROM challenge_detail WHERE id IN (:ids)")
    suspend fun deleteChallengeDetail(ids: List<Long>): Int

    @Update
    suspend fun updateChallengeDetail(challengeDetailEntity: ChallengeDetailEntity): Int

    // 세부 일정 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallengeDetail(challengeDetailEntity: List<ChallengeDetailEntity>): List<Long>

    // 일정 추가
    @Transaction
    suspend fun insertChallenge(
        challengeEntity: ChallengeEntity,
        detailChallenge: List<ChallengeDetailModel>,
        ids: List<Long>
    ): Long {
        // 1. 챌린지 추가
        val challengeId = if (challengeEntity.id == null) {
            insertChallenge(challengeEntity)
        } else {
            updateChallenge(challengeEntity)
            challengeEntity.id
        }
        // 2. 챌린지 상세 일정 추가 / 수정
        insertChallengeDetail(detailChallenge.map { it.toEntity(challengeId) })
        // 3. 일정 삭제
        deleteChallengeDetail(ids)

        return challengeId
    }

    // 해당 기간 내에 챌린지 리스트 요청
    @Query(
        "select\n" +
                "*\n" +
                "from (\n" +
                "select\n" +
                "ch.id as id,\n" +
                "ch.title as name,\n" +
                "ch.image,\n" +
                "ch.startedAt,\n" +
                "ch.endedAt,\n" +
                "ch.category_id as categoryId,\n" +
                "ca.name as categoryName,\n" +
                "ca.color as categoryColor\n" +
                "from challenge ch " +
                "left join category ca\n" +
                "on ch.category_id = ca.id\n" +
                "where :date between ch.startedAt and ch.endedAt" +
                ") c\n" +
                "left join (\n" +
                "select challenge_id, count(*) as count from completed_challenge group by challenge_id\n" +
                ") cc\n" +
                "on c.id = cc.challenge_id"
    )
    suspend fun selectChallenge(date: Long): List<MainChallengeEntity>

    @Query(
        "SELECT * FROM challenge_detail WHERE challenge_id = :challengeId"
    )
    suspend fun selectChallengeDetail(challengeId: Long): List<ChallengeDetailEntity>

    @Query("SELECT * FROM challenge WHERE id = :id")
    suspend fun selectChallengeById(id: Long): ChallengeEntity


    @Query("select\n" +
            "c.id as id,\n" +
            "c.title as name,\n" +
            "c.image,\n" +
            "c.startedAt,\n" +
            "c.endedAt,\n" +
            "c.category_id as categoryId,\n" +
            "c.name as categoryName,\n" +
            "c.color as categoryColor,\n" +
            "count(cc.id) as count \n" +
            "from (\n" +
            "select\n" +
            "*\n" +
            "from challenge ch " +
            "left join category ca \n" +
            "on ch.category_id = ca.id\n" +
            ") c\n" +
            "left join (\n" +
            "select challenge_id, id, date from completed_challenge \n" +
            ") cc\n" +
            "on c.id = cc.challenge_id \n" +
            "and cc.date >= c.startedAt and cc.date <= c.endedAt \n" +
            "group by c.id" )
    fun selectChallenge(): Flow<List<MainChallengeEntity>>

    @Query("select\n" +
            "c.id as id,\n" +
            "c.title as name,\n" +
            "c.image,\n" +
            "c.startedAt,\n" +
            "c.endedAt,\n" +
            "c.category_id as categoryId,\n" +
            "c.name as categoryName,\n" +
            "c.color as categoryColor,\n" +
            "count(cc.id) as count \n" +
            "from (\n" +
            "select\n" +
            "*\n" +
            "from challenge ch " +
            "left join category ca \n" +
            "on ch.category_id = ca.id\n" +
            "where (\n" +
            "(:startedAt <= ch.startedAt and :endedAt >= ch.endedAt)\n" +
            "or (ch.startedAt <= :startedAt and :endedAt <= ch.endedAt)\n" +
            "or (:startedAt <= ch.startedAt and ch.startedAt <= :endedAt and :endedAt <= ch.endedAt)\n" +
            "or (ch.endedAt <= :endedAt and ch.startedAt <= :startedAt and :startedAt <= ch.endedAt)\n" +
            ")\n" +
            ") c\n" +
            "left join (\n" +
            "select challenge_id, id, date from completed_challenge \n" +
            ") cc\n" +
            "on c.id = cc.challenge_id \n" +
            "and cc.date >= c.startedAt and cc.date <= c.endedAt \n" +
            "group by c.id" )
    suspend fun selectMonthlyChallenge(
        startedAt: Long,
        endedAt: Long
    ): List<MainChallengeEntity>

    @Query(
        "select\n" +
                "c.id,\n" +
                "c.name,\n" +
                "c.image,\n" +
                "c.startedAt,\n" +
                "c.endedAt,\n" +
                "c.categoryId,\n" +
                "c.categoryName,\n" +
                "c.categoryColor,\n" +
                "case\n" +
                "when cc.id is null then 0\n" +
                "else 1\n" +
                "end as count \n" +
                "from (\n" +
                "select\n" +
                "ch.id as id,\n" +
                "ch.title as name,\n" +
                "ch.image as image,\n" +
                "ch.startedAt as startedAt,\n" +
                "ch.endedAt as endedAt,\n" +
                "ca.id as categoryId,\n" +
                "ca.name as categoryName,\n" +
                "ca.color as categoryColor\n" +
                "from challenge ch " +
                "left join category ca\n" +
                "on ch.category_id = ca.id\n" +
                "where :date between ch.startedAt and ch.endedAt\n" +
                ") c\n" +
                "left join (\n" +
                "select * from completed_challenge where date = :date \n" +
                ") cc\n" +
                "on c.id = cc.challenge_id"
    )
    suspend fun selectDiaryChallenge(date: Long): List<MainChallengeEntity>

    @Query("DELETE FROM challenge WHERE id = :challengeId")
    suspend fun deleteChallenge(challengeId: Long): Int
}