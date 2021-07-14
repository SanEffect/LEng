package com.san.domain.usecases.dictionary

import com.san.domain.Result
import com.san.domain.entities.WordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IWordsRepository
import javax.inject.Inject

class GetWordFromDatabaseUseCase @Inject constructor(
    private val wordsRepository: IWordsRepository
) : UseCase<WordEntity?, GetWordFromDatabaseUseCase.Params> {

    data class Params(val word: String)

    override suspend fun invoke(params: Params): Result<WordEntity?> =
        wordsRepository.getWordEntity(params.word)
}
