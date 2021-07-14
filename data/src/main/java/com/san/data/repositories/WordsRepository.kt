package com.san.data.repositories

import com.san.data.sources.local.IWordsLocalDataSource
import com.san.data.sources.remote.IWordsRemoteDataSource
import com.san.domain.Result
import com.san.domain.Result.Error
import com.san.domain.Result.Success
import com.san.domain.entities.WordEntity
import com.san.domain.models.WordDefinitions
import com.san.domain.models.WordResponse
import com.san.domain.repositories.IWordsRepository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject

class WordsRepository @Inject constructor(
    private val wordsLocalDataSource: IWordsLocalDataSource,
    private val wordsRemoteDataSource: IWordsRemoteDataSource
) : IWordsRepository {

    private var cachedWords: ConcurrentMap<String, WordEntity>? = null

    override suspend fun getWord(word: String): Result<WordResponse?> {

        // TODO: fix it
        return when (val result = wordsLocalDataSource.getWordByName(word)) {
            is Success -> Success(result.data?.toDomain())
            is Error -> wordsRemoteDataSource.getWord(word)
        }
    }

    override suspend fun getWordDefinitions(word: String): Result<WordDefinitions> =
        wordsRemoteDataSource.getWordDefinitions(word)

    override suspend fun getWordEntity(word: String): Result<WordEntity?> =
        wordsLocalDataSource.getWordByName(word)

    override suspend fun saveWord(word: WordEntity): Result<Unit> {
        TODO("")
//        cacheAndPerform(word) {
//            wordsLocalDataSource.saveWord(word)
//        }
    }

    private fun cacheRecord(word: WordEntity): WordEntity {
        if (cachedWords == null) {
            cachedWords = ConcurrentHashMap()
        }
        val cachedWord = WordEntity(word.id, word.name, word.data)
        cachedWords?.put(cachedWord.id, cachedWord)
        return cachedWord
    }

    private inline fun cacheAndPerform(word: WordEntity, perform: (WordEntity) -> Unit) {
        val cachedWord = cacheRecord(word)
        perform(cachedWord)
    }
}
