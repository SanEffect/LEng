package com.san.data.dataAccessObjects

import androidx.room.*
import com.san.domain.entities.RecordEntity

@Dao
interface RecordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: RecordEntity)

    @Update
    suspend fun update(record: RecordEntity)

    @Query("SELECT * FROM records WHERE id = :key")
    suspend fun get(key: String): RecordEntity

    @Query("DELETE FROM records WHERE id = :key")
    suspend fun removeRecord(key: String)

    @Query("DELETE FROM records")
    suspend fun clear()

    @Query("SELECT * FROM records ORDER BY creation_date DESC")
    suspend fun getRecords(): List<RecordEntity>

    @Query("SELECT * FROM records ORDER BY creation_date DESC LIMIT 1")
    suspend fun getLastRecord(): RecordEntity

    @Query("SELECT count(*) FROM records")
    suspend fun getRecordsCount(): Long

    @Query("SELECT sum(length(trim(text))) + sum(length(trim(title))) FROM records")
    suspend fun getLettersCount(): Long

    // TODO: fix to count both fields title and text
    @Query(
        "SELECT CASE WHEN length(text) >= 1 " +
                "THEN sum(length(title) - length(replace(title, ' ', '')) + 1) + sum(length(text) - length(replace(text, ' ', '')) + 1)" +
                "ELSE sum(length(title) - length(replace(title, ' ', ''))) + sum(length(text) - length(replace(text, ' ', ''))) END as wordsCount " +
                "FROM records"
    )
//    @Query(
//        "SELECT CASE WHEN length(text) >= 1 " +
//                "THEN sum(length(text) - length(replace(text, ' ', '')) + 1) " +
//                "ELSE sum(length(text) - length(replace(text, ' ', ''))) END as wordsCount " +
//                "FROM records"
//    )
    suspend fun getWordsCount(): Long
}

