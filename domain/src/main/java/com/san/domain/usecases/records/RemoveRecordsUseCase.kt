package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.interactor.UseCase
import com.san.domain.repositories.IRecordsRepository
import javax.inject.Inject

class RemoveRecordsUseCase @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Unit, Unit> {

    override suspend fun invoke(params: Unit): Result<Unit> = recordsRepository.removeRecords()
}
