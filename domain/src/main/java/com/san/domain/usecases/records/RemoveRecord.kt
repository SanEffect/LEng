package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveRecord @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Unit, RemoveRecord.Params>() {

    data class Params(val recordId: Long)

    override suspend fun run(params: Params): Result<Unit> = withContext(Dispatchers.IO) {
        recordsRepository.removeRecord(params.recordId)
    }
}