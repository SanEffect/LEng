package com.san.domain.repositories

import com.san.domain.Result
import com.san.domain.entities.WordResult

interface IWordsApiRemoteRepository {
    suspend fun getWordDefinitions(word: String): Result<WordResult>
}