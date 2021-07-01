package com.san.domain.usecases.dictionary

import com.san.domain.Result
import com.san.domain.interactor.UseCase
import com.san.domain.models.WordResponse
import com.san.domain.repositories.IWordsApiRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWord @Inject constructor(
    private val wordsApiRemoteRepository: IWordsApiRemoteRepository
) : UseCase<WordResponse, GetWord.Params>() {

    data class Params(val word: String)

    override suspend fun run(params: Params): Result<WordResponse> = withContext(Dispatchers.IO) {
        wordsApiRemoteRepository.getWord(params.word)
    }

}
