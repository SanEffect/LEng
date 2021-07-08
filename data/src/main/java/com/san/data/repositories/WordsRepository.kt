package com.san.data.repositories

import com.san.data.sources.local.IWordsLocalDataSource
import com.san.data.sources.remote.IWordsRemoteDataSource
import com.san.domain.Result
import com.san.domain.entities.WordEntity
import com.san.domain.models.WordDefinitions
import com.san.domain.models.WordResponse
import com.san.domain.repositories.IWordsRepository
import javax.inject.Inject

class WordsRepository @Inject constructor(
    private val wordsLocalDataSource: IWordsLocalDataSource,
    private val wordsRemoteDataSource: IWordsRemoteDataSource
) : IWordsRepository {

    override suspend fun insert(wordEntity: WordEntity): Result<Unit> =
        wordsLocalDataSource.insert(wordEntity)

    override suspend fun getWord(word: String): Result<WordResponse> =
        wordsRemoteDataSource.getWord(word)

    override suspend fun getWordDefinitions(word: String): Result<WordDefinitions> =
        wordsRemoteDataSource.getWordDefinitions(word)

    override suspend fun getWordEntity(word: String): Result<WordEntity> =
        wordsLocalDataSource.getWordByName(word)

}
