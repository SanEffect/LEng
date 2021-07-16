package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import javax.inject.Inject

class DeleteRecordsUseCase @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Unit, DeleteRecordsUseCase.Params> {

    data class Params(val ids: List<String>)

    override suspend fun invoke(params: Params): Result<Unit> =
        recordsRepository.deleteRecords(params.ids)
}
