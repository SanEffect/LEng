package com.san.data.sources.remote

import com.san.domain.Result
import com.san.domain.models.WordDefinitions
import com.san.domain.models.WordResponse
import com.san.leng.core.extensions.doRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WordsRemoteDataSource @Inject constructor(
    private val wordsApiService: WordApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IWordsRemoteDataSource {

    override suspend fun getWord(word: String): Result<WordResponse> = doRequest(ioDispatcher) {
        wordsApiService.getWordAsync(word)
    }

    override suspend fun getWordDefinitions(word: String): Result<WordDefinitions> =
        doRequest(ioDispatcher) {
            wordsApiService.getWordDefinitionsAsync(word)
        }

}
