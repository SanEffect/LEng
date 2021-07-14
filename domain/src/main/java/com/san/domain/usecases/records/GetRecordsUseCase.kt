package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import javax.inject.Inject

class GetRecordsUseCase @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<List<RecordEntity>, Unit> {

    override suspend fun invoke(params: Unit): Result<List<RecordEntity>> =
        recordsRepository.getRecords()
}
