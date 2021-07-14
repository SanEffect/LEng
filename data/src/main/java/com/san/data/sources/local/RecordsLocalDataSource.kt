package com.san.data.sources.local

import com.san.data.dataAccessObjects.RecordsDao
import com.san.data.extensions.doQuery
import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecordsLocalDataSource @Inject constructor(
    private val recordsDao: RecordsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRecordsLocalDataSource {

    @ExperimentalCoroutinesApi
    override val records: Flow<List<RecordEntity>> = flow {
        val records = recordsDao.getRecords()
        emit(records)
    }.flowOn(Dispatchers.IO)

    override suspend fun getRecords(): Result<List<RecordEntity>> =
        doQuery(ioDispatcher) {
            recordsDao.getRecords()
        }

    override suspend fun getLastRecord(): Result<RecordEntity> =
        doQuery(ioDispatcher) {
            recordsDao.getLastRecord()
        }

    override suspend fun saveRecord(record: RecordEntity): Result<Unit> =
        doQuery(ioDispatcher) {
            recordsDao.insert(record)
        }

    override suspend fun update(record: RecordEntity): Result<Unit> =
        doQuery(ioDispatcher) {
            recordsDao.update(record)
        }

    override suspend fun getById(id: String): Result<RecordEntity> =
        doQuery(ioDispatcher) {
            recordsDao.get(id)
        }

    override suspend fun getRecordsCount(): Result<Long> =
        doQuery(ioDispatcher) {
            recordsDao.getRecordsCount()
        }

    override suspend fun removeRecord(recordId: String): Result<Unit> =
        doQuery(ioDispatcher) {
            recordsDao.removeRecord(recordId)
        }

    override suspend fun removeRecords(): Result<Unit> =
        doQuery(ioDispatcher) {
            recordsDao.clear()
        }

    override suspend fun getWordsCount(): Result<Long> =
        doQuery(ioDispatcher) {
            recordsDao.getWordsCount()
        }
}
