package com.san.data.repositories

import com.san.data.sources.local.IRecordsLocalDataSource
import com.san.domain.Either
import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import com.san.domain.exception.Failure
import com.san.domain.repositories.IRecordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject

class RecordsRepository @Inject constructor(
    private val recordsLocalDataSource: IRecordsLocalDataSource
) : IRecordsRepository {

    private var cachedRecords: ConcurrentMap<String, RecordEntity>? = null

    override val records: Flow<List<RecordEntity>> =
        recordsLocalDataSource.records
            .map { records -> records.filter { !it.isDeleted } }

    override suspend fun getRecords(): Result<List<RecordEntity>> {
        return recordsLocalDataSource.getRecords()
    }

    override suspend fun refreshRecords() {
        TODO("Not yet implemented")
    }

    override suspend fun getLastRecord(): Result<RecordEntity?> =
        recordsLocalDataSource.getLastRecord()

    override suspend fun saveRecord(record: RecordEntity): Result<Unit> =
        recordsLocalDataSource.saveRecord(record)

    override suspend fun update(record: RecordEntity): Result<Unit> =
        recordsLocalDataSource.update(record)

    override suspend fun getById(id: String): Result<RecordEntity?> = recordsLocalDataSource.getById(id)

    override suspend fun getRecordsCount() = recordsLocalDataSource.getRecordsCount()

    override suspend fun removeRecord(recordId: String): Result<Unit> = recordsLocalDataSource.removeRecord(recordId)

    override suspend fun removeRecords(): Result<Unit> = recordsLocalDataSource.removeRecords()

    override suspend fun getWordsCount(): Result<Long> = recordsLocalDataSource.getWordsCount()
}
