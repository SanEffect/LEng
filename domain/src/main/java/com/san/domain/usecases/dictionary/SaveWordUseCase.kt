package com.san.domain.usecases.dictionary

import com.san.domain.Result
import com.san.domain.entities.WordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IWordsRepository
import javax.inject.Inject

class SaveWordUseCase @Inject constructor(
    private val wordsRepository: IWordsRepository
) : UseCase<Unit, SaveWordUseCase.Params> {

    data class Params(val wordEntity: WordEntity)

    override suspend fun invoke(params: Params): Result<Unit> =
        wordsRepository.saveWord(params.wordEntity)
}
