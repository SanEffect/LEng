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
        private val localDataSource: IRecordsDataSource,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRecordsRepository {

    override val records: Flow<List<RecordEntity>> =
        localDataSource.records
            .map { records -> records.filter { !it.isDeleted } }

    override suspend fun getRecords(): Result<List<RecordEntity>> {
        return localDataSource.getRecords()
    }

    override suspend fun refreshRecords() {
        TODO("Not yet implemented")
    }

    override suspend fun getLastRecord(): Result<RecordEntity> {
        return withContext(ioDispatcher) {
            localDataSource.getLastRecord()
        }
    }

    override suspend fun insert(record: RecordEntity): Result<Unit> = withContext(ioDispatcher) {
        localDataSource.insert(record)
    }

    override suspend fun update(record: RecordEntity): Result<Unit> = withContext(ioDispatcher) {
        localDataSource.update(record)
    }

    override suspend fun getById(id: Long) : Result<RecordEntity?> {
        return withContext(ioDispatcher) {
            localDataSource.getById(id)
        }
    }

    override suspend fun getRecordsCount() = withContext(ioDispatcher) {
        localDataSource.getRecordsCount()
    }

    override suspend fun removeRecords(): Result<Unit> = withContext(ioDispatcher) {
        localDataSource.removeRecords()
    }

    override suspend fun getWordsCount(): Result<Long> = withContext(ioDispatcher) {
        localDataSource.getWordsCount()
    }
}