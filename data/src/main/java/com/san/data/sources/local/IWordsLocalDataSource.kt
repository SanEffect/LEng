package com.san.data.sources.local

import com.san.domain.Result
import com.san.domain.entities.WordEntity

interface IWordsLocalDataSource {

    suspend fun insert(wordEntity: WordEntity): Result<Unit>

    suspend fun getWordByName(word: String): Result<WordEntity>
}
