package com.san.domain.usecases.dictionary

import com.san.domain.Result
import com.san.domain.entities.WordResult
import com.san.domain.interactor.UseCase
import com.san.domain.usecases.dictionary.GetWordDefinitions.Params
import com.san.domain.repositories.IWordsApiRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWordDefinitions @Inject constructor(
    private val wordsApiRemoteRepository: IWordsApiRemoteRepository
) : UseCase<WordResult, Params>() {

    override suspend fun run(params: Params): Result<WordResult> = withContext(Dispatchers.IO) {
        wordsApiRemoteRepository.getWordDefinitions(params.word)
    }

    data class Params(val word: String)
}