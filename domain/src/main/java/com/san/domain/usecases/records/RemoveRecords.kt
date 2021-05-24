package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.interactor.UseCase
import com.san.domain.interactor.UseCase.None
import com.san.domain.repositories.IRecordsRepository
import javax.inject.Inject

class RemoveRecords @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Unit, None>() {

    override suspend fun run(params: None): Result<Unit> {
        return recordsRepository.removeRecords()
    }
}