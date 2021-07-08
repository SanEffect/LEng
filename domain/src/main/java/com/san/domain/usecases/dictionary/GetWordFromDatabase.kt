package com.san.domain.usecases.dictionary

import com.san.domain.Result
import com.san.domain.entities.WordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IWordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWordFromDatabase @Inject constructor(
    private val wordsRepository: IWordsRepository
) : UseCase<WordEntity, GetWordFromDatabase.Params>() {

    data class Params(val word: String)

    override suspend fun run(params: Params): Result<WordEntity> = withContext(Dispatchers.IO) {
        wordsRepository.getWordEntity(params.word)
    }
}
