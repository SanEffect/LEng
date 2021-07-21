package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import javax.inject.Inject

class GetRecordUseCase @Inject constructor(
    private val recordRepository: IRecordsRepository
) : UseCase<RecordEntity?, GetRecordUseCase.Params> {

    data class Params(val recordId: String, val forceUpdate: Boolean)

    override suspend fun invoke(params: Params): Result<RecordEntity?> =
        recordRepository.getRecordById(params.recordId, params.forceUpdate)
}
