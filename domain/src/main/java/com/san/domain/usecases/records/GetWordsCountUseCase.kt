package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import javax.inject.Inject

class GetWordsCountUseCase @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Long, Unit> {

    override suspend fun invoke(params: Unit): Result<Long> = recordsRepository.getWordsCount()
}
