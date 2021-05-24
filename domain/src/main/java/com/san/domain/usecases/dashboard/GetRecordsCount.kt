package com.san.domain.usecases.dashboard

import com.san.domain.Result
import com.san.domain.interactor.UseCase
import com.san.domain.interactor.UseCase.None
import com.san.domain.repositories.IRecordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRecordsCount @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Long, None>() {

    override suspend fun run(params: None): Result<Long> = withContext(Dispatchers.IO) {
        recordsRepository.getRecordsCount()
    }
}