package com.san.data.dataAccessObjects

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.san.domain.entities.WordEntity

@Dao
interface WordsDao {

    @Insert
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
