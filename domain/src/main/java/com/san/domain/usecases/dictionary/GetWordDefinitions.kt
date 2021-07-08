package com.san.domain.usecases.dictionary

import com.san.domain.Result
import com.san.domain.interactor.UseCase
import com.san.domain.models.WordDefinitions
import com.san.domain.repositories.IWordsRepository
import com.san.domain.usecases.dictionary.GetWordDefinitions.Params
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWordDefinitions @Inject constructor(
    private val wordsApiRepository: IWordsRepository
) : UseCase<WordDefinitions, Params>() {

    data class Params(val word: String)

    override suspend fun run(params: Params): Result<WordDefinitions> =
        withContext(Dispatchers.IO) {
            wordsApiRepository.getWordDefinitions(params.word)
        }
}
