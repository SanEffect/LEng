package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import javax.inject.Inject

class GetRecordsUseCase @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<List<RecordEntity>, GetRecordsUseCase.Params> {

    data class Params(val forceUpdate: Boolean = false)

    override suspend fun invoke(params: Params): Result<List<RecordEntity>> =
        recordsRepository.getRecords(params.forceUpdate)
}
