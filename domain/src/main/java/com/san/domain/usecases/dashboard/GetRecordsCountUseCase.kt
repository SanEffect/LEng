package com.san.domain.usecases.dashboard

import com.san.domain.Result
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import javax.inject.Inject

class GetRecordsCountUseCase @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Long, Unit> {

    override suspend fun invoke(params: Unit): Result<Long> = recordsRepository.getRecordsCount()
}
