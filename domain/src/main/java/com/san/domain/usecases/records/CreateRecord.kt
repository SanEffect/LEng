package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import javax.inject.Inject

class CreateRecord @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Unit, CreateRecord.Params>() {

    data class Params(val record: RecordEntity, val update: Boolean = false)

    override suspend fun run(params: Params): Result<Unit> {
        return if (params.update)
            recordsRepository.update(params.record)
        else
            recordsRepository.insert(params.record)
    }
}
