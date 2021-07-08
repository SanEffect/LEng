package com.san.domain.repositories

import com.san.domain.Result
import com.san.domain.entities.WordEntity
import com.san.domain.models.WordDefinitions
import com.san.domain.models.WordResponse

interface IWordsRepository {
    
    suspend fun insert(wordEntity: WordEntity): Result<Unit>

    suspend fun getWord(word: String): Result<WordResponse>

    suspend fun getWordDefinitions(word: String): Result<WordDefinitions>

    suspend fun getWordEntity(word: String): Result<WordEntity>
}
