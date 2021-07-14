package com.san.data.dataAccessObjects

import androidx.room.*
import com.san.domain.entities.WordEntity

@Dao
interface WordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: WordEntity)

    @Insert
    suspend fun insertList(words: List<WordEntity>)

    @Update
    suspend fun update(word: WordEntity)

    @Query("SELECT * FROM words")
    suspend fun getWords(): List<WordEntity>

    @Query("SELECT * FROM words WHERE name = :word")
    suspend fun getWordByName(word: String): WordEntity?
}
