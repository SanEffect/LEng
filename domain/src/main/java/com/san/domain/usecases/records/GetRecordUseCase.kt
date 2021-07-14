package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import javax.inject.Inject

class GetRecordUseCase @Inject constructor(
    private val recordRepository: IRecordsRepository
) : UseCase<RecordEntity?, GetRecordUseCase.Params> {

    data class Params(val recordId: String)

    override suspend fun invoke(params: Params): Result<RecordEntity?> =
        recordRepository.getById(params.recordId)
}
