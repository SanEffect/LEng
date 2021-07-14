package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import javax.inject.Inject

class SaveRecordUseCase @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Unit, SaveRecordUseCase.Params> {

    data class Params(val record: RecordEntity)

    override suspend fun invoke(params: Params): Result<Unit> {
        return recordsRepository.saveRecord(params.record)
    }
}
