package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.Result.Success
import com.san.domain.Result.Error
import com.san.domain.entities.RecordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import java.lang.Exception
import javax.inject.Inject

class SaveRecordUseCase @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Unit, SaveRecordUseCase.Params> {

    data class Params(val record: RecordEntity)

    override suspend fun invoke(params: Params): Result<Unit> {
        return try {
            recordsRepository.saveRecord(params.record)
            Success(Unit)
        } catch (e: Exception) {
            Error(e)
        }
    }
}
