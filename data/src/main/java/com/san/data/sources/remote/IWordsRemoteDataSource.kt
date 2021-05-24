package com.san.data.sources.remote

import com.san.domain.Result
import com.san.domain.entities.WordResult

interface IWordsRemoteDataSource {
    suspend fun getWordDefinitionsAsync(word: String) : Result<WordResult>
}