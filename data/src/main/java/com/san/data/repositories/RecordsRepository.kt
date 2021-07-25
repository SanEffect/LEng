package com.san.data.repositories

import com.san.data.extensions.performIfSuccess
import com.san.data.sources.local.IRecordsLocalDataSource
import com.san.domain.Result
import com.san.domain.Result.Failure
import com.san.domain.Result.Success
import com.san.domain.entities.RecordEntity
import com.san.domain.repositories.IRecordsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
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
                    Timber.tag("RecordsRepository").i("getRecords from cache")
                    return@withContext Success(cachedRecords.values.sortedByDescending { it.creationDate })
                }
            }

            Timber.tag("RecordsRepository").i("getRecords from DB")
            val newRecords = recordsLocalDataSource.getRecords()

            // Refresh the cache with the new records
            (newRecords as? Success)?.let { refreshCache(it.data) }

            cachedRecords?.values?.let { records ->
                return@withContext Success(records.sortedByDescending { it.creationDate })
            }
            return@withContext Failure(Exception("Illegal state"))
        }
    }

    override suspend fun getRecordById(id: String, forceUpdate: Boolean): Result<RecordEntity> {
        return withContext(ioDispatcher) {
            // Respond immediately with cache if available
            if (!forceUpdate) {
                getRecordWithId(id)?.let {
                    Timber.tag("RecordsRepository").i("getRecordById from cache")
                    return@withContext Success(it)
                }
            }

            Timber.tag("RecordsRepository").i("getRecordById from DB")
            val newRecord = recordsLocalDataSource.getById(id)

            // Refresh the cache with the new records
            (newRecord as? Success)?.let { cacheRecord(it.data) }

            return@withContext newRecord
        }
    }

    private fun getRecordWithId(recordId: String) = cachedRecords?.get(recordId)

    override suspend fun refreshRecords() {
        TODO("Not yet implemented")
    }

    override suspend fun getLastRecord(): Result<RecordEntity?> =
        recordsLocalDataSource.getLastRecord()

    override suspend fun saveRecord(record: RecordEntity): Result<Unit> {
        return cacheAndPerform(record) {
            recordsLocalDataSource.saveRecord(record)
        }
    }

    override suspend fun getRecordsCount() = recordsLocalDataSource.getRecordsCount()

    override suspend fun removeRecord(recordId: String): Result<Unit> {
        return performIfSuccess(recordsLocalDataSource.removeRecord(recordId)) {
            cachedRecords?.remove(recordId)
        }
    }

    override suspend fun removeRecords(): Result<Unit> {
        return performIfSuccess(recordsLocalDataSource.removeRecords()) {
            cachedRecords?.entries?.clear()
        }
    }

    override suspend fun deleteRecords(recordIds: List<String>): Result<Unit> {

        val result = recordsLocalDataSource.deleteRecords(recordIds)
        if (result is Success)
            recordIds.forEach { recId -> cachedRecords?.remove(recId) }

        return result
    }

    override suspend fun getWordsCount(): Result<Long> = recordsLocalDataSource.getWordsCount()

    private fun refreshCache(records: List<RecordEntity>) {
        if (cachedRecords == null) {
            cachedRecords = ConcurrentHashMap()
        }

        cachedRecords?.apply {
            clear()
            records
                .sortedBy { it.creationDate }
                .forEach { put(it.id, it) }
        }
    }

    private suspend fun <Type> cacheAndPerform(
        record: RecordEntity,
        perform: suspend (RecordEntity) -> Result<Type>
    ): Result<Type> {
        val cachedRecord = cacheRecord(record)

        return withContext(ioDispatcher) {
            perform(cachedRecord)
        }
    }

//    private inline fun cacheAndPerform(record: RecordEntity, perform: (RecordEntity) -> Unit) {
//        val cachedRecord = cacheRecord(record)
//        perform(cachedRecord)
//    }

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
            record.isDraft,
            record.backgroundColor,
            record.id
        )
        cachedRecords?.put(cachedRecord.id, cachedRecord)
        return cachedRecord
    }
}
