package com.san.domain.usecases.records

import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import com.san.domain.interactor.UseCase
import com.san.domain.interactor.UseCase.None
import com.san.domain.repositories.IRecordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRecords @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<List<RecordEntity>, None>() {

    override suspend fun run(params: None): Result<List<RecordEntity>> =
        withContext(Dispatchers.IO) {
            recordsRepository.getRecords()
        }
}
