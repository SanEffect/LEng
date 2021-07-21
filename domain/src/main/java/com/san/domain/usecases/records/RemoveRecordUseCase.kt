package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import javax.inject.Inject

class RemoveRecordUseCase @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Unit, RemoveRecordUseCase.Params> {

    data class Params(val recordId: String)

    override suspend fun invoke(params: Params): Result<Unit> = recordsRepository.removeRecord(params.recordId)
}
