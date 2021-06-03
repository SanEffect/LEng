package com.san.data.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.san.domain.entities.RecordEntity

@Dao
interface RecordsDao {

    @Insert
    suspend fun insert(record: RecordEntity)

    @Update
    suspend fun update(record: RecordEntity)

    @Query("SELECT * FROM records WHERE id = :key")
    suspend fun get(key: Long) : RecordEntity

    @Query("DELETE FROM records WHERE id = :key")
    suspend fun removeRecord(key: Long)

    @Query("DELETE FROM records")
    suspend fun clear()

    @Query("SELECT * FROM records ORDER BY id DESC")
    suspend fun getRecords(): List<RecordEntity>

    @Query("SELECT * FROM records ORDER BY id DESC LIMIT 1")
    suspend fun getLastRecord() : RecordEntity

    @Query("SELECT count(*) FROM records")
    suspend fun getRecordsCount() : Long

    @Query("SELECT sum(length(trim(text))) + sum(length(trim(title))) FROM records")
    suspend fun getLettersCount() : Long

    @Query("SELECT CASE WHEN length(text) >= 1 " +
            "THEN sum(length(text) - length(replace(text, ' ', '')) + 1) " +
            "ELSE sum(length(text) - length(replace(text, ' ', ''))) END as wordsCount " +
            "FROM records")
    suspend fun getWordsCount() : Long
}

