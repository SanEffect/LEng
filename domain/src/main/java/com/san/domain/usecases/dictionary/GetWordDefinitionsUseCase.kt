package com.san.domain.usecases.dictionary

import com.san.domain.Result
import com.san.domain.interactor.UseCase
import com.san.domain.models.WordDefinitions
import com.san.domain.repositories.IWordsRepository
import com.san.domain.usecases.dictionary.GetWordDefinitionsUseCase.Params
import javax.inject.Inject

class GetWordDefinitionsUseCase @Inject constructor(
    private val wordsApiRepository: IWordsRepository
) : UseCase<WordDefinitions, Params> {

    data class Params(val word: String)

    override suspend fun invoke(params: Params): Result<WordDefinitions> =
        wordsApiRepository.getWordDefinitions(params.word)
}
