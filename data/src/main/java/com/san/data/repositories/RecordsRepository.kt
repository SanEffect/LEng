package com.san.data.repositories

import com.san.data.sources.local.IRecordsLocalDataSource
import com.san.domain.Result
import com.san.domain.Result.Error
import com.san.domain.Result.Success
import com.san.domain.entities.RecordEntity
import com.san.domain.repositories.IRecordsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject

class RecordsRepository @Inject constructor(
    private val recordsLocalDataSource: IRecordsLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRecordsRepository {

    private var cachedRecords: ConcurrentMap<String, RecordEntity>? = null

    override val records: Flow<List<RecordEntity>> =
        recordsLocalDataSource.records
            .map { records -> records.filter { !it.isDeleted } }

    override suspend fun getRecords(forceUpdate: Boolean): Result<List<RecordEntity>> {

        return withContext(ioDispatcher) {

            if (!forceUpdate) {
                cachedRecords?.let { cachedRecords ->
                    return@withContext Success(cachedRecords.values.sortedBy { it.id })
                }
            }

            val newRecords = recordsLocalDataSource.getRecords()

            // Refresh the cache with the new records
            (newRecords as? Success)?.let { refreshCache(it.data) }

            cachedRecords?.values?.let { records ->
                return@withContext Success(records.sortedBy { it.id })
            }

            return@withContext Error(Exception("Illegal state"))
        }
    }

    private inline fun cacheAndPerform(record: RecordEntity, perform: (RecordEntity) -> Unit) {
        val cachedRecord = cacheRecord(record)
        perform(cachedRecord)
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

    override suspend fun getById(id: String): Result<RecordEntity?> =
        recordsLocalDataSource.getById(id)

    override suspend fun getRecordsCount() = recordsLocalDataSource.getRecordsCount()

    override suspend fun removeRecord(recordId: String): Result<Unit> =
        recordsLocalDataSource.removeRecord(recordId)

    override suspend fun removeRecords(): Result<Unit> = recordsLocalDataSource.removeRecords()

    override suspend fun getWordsCount(): Result<Long> = recordsLocalDataSource.getWordsCount()

    private fun refreshCache(records: List<RecordEntity>) {
        cachedRecords?.clear()
        records.sortedBy { it.id }.forEach {
            cacheAndPerform(it) {}
        }
    }

    private fun cacheRecord(record: RecordEntity): RecordEntity {
        // Create if it doesn't exist.
        if (cachedRecords == null) {
            cachedRecords = ConcurrentHashMap()
        }

        val cachedRecord = RecordEntity(
            record.title,
            record.description,
            record.creationDate,
            record.isDeleted,
            record.id
        )
        cachedRecords?.put(cachedRecord.id, cachedRecord)
        return cachedRecord
    }
}
