package com.san.data.repositories

import com.san.data.sources.remote.IWordsRemoteDataSource
import com.san.domain.Result
import com.san.domain.models.WordDefinitions
import com.san.domain.models.WordResponse
import com.san.domain.repositories.IWordsApiRemoteRepository
import javax.inject.Inject

class WordsApiRepository @Inject constructor(
    private val wordsRemoteDataSource: IWordsRemoteDataSource
) : IWordsApiRemoteRepository {

    override suspend fun getWord(word: String): Result<WordResponse> =
        wordsRemoteDataSource.getWord(word)

    override suspend fun getWordDefinitions(word: String): Result<WordDefinitions> =
        wordsRemoteDataSource.getWordDefinitions(word)

}
