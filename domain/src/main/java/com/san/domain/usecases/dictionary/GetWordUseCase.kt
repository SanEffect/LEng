package com.san.domain.usecases.dictionary

import com.san.domain.interactor.UseCase
import com.san.domain.models.WordResponse
import com.san.domain.repositories.IWordsRepository
import javax.inject.Inject
import com.san.domain.Result

class GetWordUseCase @Inject constructor(
    private val wordsRepository: IWordsRepository
) : UseCase<WordResponse?, GetWordUseCase.Params> {

    data class Params(val word: String)

    override suspend fun invoke(params: Params): Result<WordResponse?> =
        wordsRepository.getWord(params.word)
}
