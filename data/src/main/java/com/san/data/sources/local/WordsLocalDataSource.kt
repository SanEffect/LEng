package com.san.data.sources.local

import com.san.data.dataAccessObjects.WordsDao
import com.san.data.extensions.doQuery
import com.san.domain.Result
import com.san.domain.entities.WordEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WordsLocalDataSource @Inject constructor(
    private val wordsDao: WordsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IWordsLocalDataSource {

    override suspend fun saveWord(wordEntity: WordEntity): Result<Unit> =
        doQuery(ioDispatcher) {
            wordsDao.insert(wordEntity)
        }

    override suspend fun getWordByName(word: String): Result<WordEntity?> =
        doQuery(ioDispatcher) {
            wordsDao.getWordByName(word)
        }

/*    override suspend fun getWordByName(word: String): Result<WordEntity> =
        withContext(ioDispatcher) {
            try {
                when (val result = wordsDao.getWordByName(word)) {
                    is WordEntity -> Success(result)
                    null -> Error(Exception("Word not found"))
                    else -> Error(Exception(""))
                }
            } catch (e: Exception) {
                Error(e)
            }
        }*/
}
