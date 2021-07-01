package com.san.data.sources.remote

import com.san.domain.Result
import com.san.domain.models.WordDefinitions
import com.san.domain.models.WordResponse

interface IWordsRemoteDataSource {

    suspend fun getWord(word: String): Result<WordResponse>

    suspend fun getWordDefinitions(word: String): Result<WordDefinitions>

}
