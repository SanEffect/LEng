package com.san.data.sources.local

import com.san.data.dataAccessObjects.RecordsDao
import com.san.domain.Result
import com.san.domain.Result.Error
import com.san.domain.Result.Success
import com.san.domain.entities.RecordEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IRecordsLocalDataSource @Inject constructor(
    private val recordsDao: RecordsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRecordsDataSource {

    @ExperimentalCoroutinesApi
    override val records: Flow<List<RecordEntity>> = flow {
        val records = recordsDao.getRecords()
        emit(records)
    }.flowOn(Dispatchers.IO)

    override suspend fun getRecords(): Result<List<RecordEntity>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(recordsDao.getRecords())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getLastRecord(): Result<RecordEntity> = withContext(ioDispatcher) {
        return@withContext try {
            Success(recordsDao.getLastRecord())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun insert(record: RecordEntity): Result<Unit> = withContext(ioDispatcher) {
        return@withContext try {
            Success(recordsDao.insert(record))
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun update(record: RecordEntity): Result<Unit> = withContext(ioDispatcher) {
        return@withContext try {
            Success(recordsDao.update(record))
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getById(id: Long): Result<RecordEntity?> = withContext(ioDispatcher) {
        return@withContext try {
            Success(recordsDao.get(id))
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getRecordsCount(): Result<Long> = withContext(ioDispatcher) {
        return@withContext try {
            Success(recordsDao.getRecordsCount())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun removeRecord(recordId: Long): Result<Unit> = withContext(ioDispatcher) {
        return@withContext try {
            recordsDao.removeRecord(recordId)
            Success(Unit)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun removeRecords(): Result<Unit> = withContext(ioDispatcher) {
        return@withContext try {
            recordsDao.clear()
            Success(Unit)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getWordsCount(): Result<Long> = withContext(ioDispatcher) {

        return@withContext try {
            Success(recordsDao.getWordsCount())
        } catch (e: Exception) {
            Error(e)
        }
    }
}
