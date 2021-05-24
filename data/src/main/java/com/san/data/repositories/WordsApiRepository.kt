package com.san.data.repositories

import com.san.data.sources.remote.WordApiService
import com.san.domain.repositories.IWordsApiRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.san.domain.Result
import com.san.domain.Result.Success
import com.san.domain.Result.Error
import com.san.domain.entities.WordResult

class WordsApiRepository @Inject constructor(
//        private val remoteDataSource: IWordsRemoteDataSource,
    private val wordsService: WordApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IWordsApiRemoteRepository {

    override suspend fun getWordDefinitions(word: String): Result<WordResult> = withContext(ioDispatcher) {
        return@withContext try {
            Success(wordsService.getWordDefinitionsAsync(word).await())
        } catch (e: Exception) {
            Error(e)
        }
    }
}