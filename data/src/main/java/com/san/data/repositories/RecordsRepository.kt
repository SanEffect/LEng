package com.san.data.repositories

import com.san.data.sources.local.IRecordsLocalDataSource
import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import com.san.domain.repositories.IRecordsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecordsRepository @Inject constructor(
    private val recordsLocalDataSource: IRecordsLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRecordsRepository {

    override val records: Flow<List<RecordEntity>> =
        recordsLocalDataSource.records
            .map { records -> records.filter { !it.isDeleted } }

    override suspend fun getRecords(): Result<List<RecordEntity>> {
        return recordsLocalDataSource.getRecords()
    }

    override suspend fun refreshRecords() {
        TODO("Not yet implemented")
    }

    override suspend fun getLastRecord(): Result<RecordEntity> {
        return withContext(ioDispatcher) {
            recordsLocalDataSource.getLastRecord()
        }
    }

    override suspend fun insert(record: RecordEntity): Result<Unit> = withContext(ioDispatcher) {
        recordsLocalDataSource.insert(record)
    }

    override suspend fun update(record: RecordEntity): Result<Unit> = withContext(ioDispatcher) {
        recordsLocalDataSource.update(record)
    }

    override suspend fun getById(id: String): Result<RecordEntity?> {
        return withContext(ioDispatcher) {
            recordsLocalDataSource.getById(id)
        }
    }

    override suspend fun getRecordsCount() = withContext(ioDispatcher) {
        recordsLocalDataSource.getRecordsCount()
    }

    override suspend fun removeRecord(recordId: String): Result<Unit> = withContext(ioDispatcher) {
        recordsLocalDataSource.removeRecord(recordId)
    }

    override suspend fun removeRecords(): Result<Unit> = withContext(ioDispatcher) {
        recordsLocalDataSource.removeRecords()
    }

    override suspend fun getWordsCount(): Result<Long> = withContext(ioDispatcher) {
        recordsLocalDataSource.getWordsCount()
    }
}
