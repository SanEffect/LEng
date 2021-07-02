package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateRecord @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Unit, UpdateRecord.Params>() {

    data class Params(val record: RecordEntity)

    override suspend fun run(params: Params): Result<Unit> =
        withContext(Dispatchers.IO) {
            recordsRepository.update(params.record)
        }
}
