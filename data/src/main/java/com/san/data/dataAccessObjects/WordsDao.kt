package com.san.data.dataAccessObjects

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.san.domain.entities.RecordEntity
import com.san.domain.entities.WordEntity

@Dao
interface WordsDao {

    @Insert
    suspend fun insert(word: WordEntity)

    @Insert
    suspend fun insertAll(words: List<WordEntity>)

    @Update
    suspend fun update(record: RecordEntity)

    @Query("SELECT * FROM words")
    suspend fun getAllWords(): List<WordEntity>
}
