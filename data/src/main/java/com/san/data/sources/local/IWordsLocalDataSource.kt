package com.san.data.sources.local

import com.san.domain.Either
import com.san.domain.Result
import com.san.domain.entities.WordEntity
import com.san.domain.exception.Failure

interface IWordsLocalDataSource {

    suspend fun saveWord(wordEntity: WordEntity): Result<Unit>

    suspend fun getWordByName(word: String): Result<WordEntity?>
}
