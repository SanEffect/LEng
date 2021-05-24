package com.san.data.repositories

import com.san.domain.entities.RecordEntity
import com.san.domain.repositories.IRecordsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.san.domain.Result
import com.san.data.sources.local.IRecordsDataSource

class RecordsRepository @Inject constructor(
        private val localDataSourceI: IRecordsDataSource,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRecordsRepository {

    override val records: Flow<List<RecordEntity>> =
        localDataSourceI.records
            .map { records -> records.filter { !it.isDeleted } }

    override suspend fun getRecords(): Result<List<RecordEntity>> {
        return localDataSourceI.getRecords()
    }

    override suspend fun refreshRecords() {
        TODO("Not yet implemented")
    }

    override suspend fun getLastRecord(): Result<RecordEntity> {
        return withContext(ioDispatcher) {
            localDataSourceI.getLastRecord()
        }
    }

    override suspend fun insert(record: RecordEntity) = withContext(ioDispatcher) {
        localDataSourceI.insert(record)
    }

    override suspend fun getById(id: Long) : Result<RecordEntity?> {
        return withContext(ioDispatcher) {
            localDataSourceI.getById(id)
        }
    }

    override suspend fun getRecordsCount() = withContext(ioDispatcher) {
        localDataSourceI.getRecordsCount()
    }

    override suspend fun removeRecords(): Result<Unit> = withContext(ioDispatcher) {
        localDataSourceI.removeRecords()
    }

    override suspend fun getWordsCount(): Result<Long> = withContext(ioDispatcher) {
        localDataSourceI.getWordsCount()
    }
}