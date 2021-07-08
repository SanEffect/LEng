package com.san.domain.usecases.dictionary

import com.san.domain.Result
import com.san.domain.entities.WordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IWordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertWord @Inject constructor(
    private val wordsRepository: IWordsRepository
) : UseCase<Unit, InsertWord.Params>() {

    data class Params(val wordEntity: WordEntity)

    override suspend fun run(params: Params): Result<Unit> = withContext(Dispatchers.IO) {
        wordsRepository.insert(params.wordEntity)
    }

}
