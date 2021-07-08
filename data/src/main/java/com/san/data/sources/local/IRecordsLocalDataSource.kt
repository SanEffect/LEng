package com.san.data.sources.local

import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import kotlinx.coroutines.flow.Flow

interface IRecordsLocalDataSource {

    val records: Flow<List<RecordEntity>>

    suspend fun getRecords(): Result<List<RecordEntity>>

    suspend fun getLastRecord(): Result<RecordEntity>

    suspend fun insert(record: RecordEntity): Result<Unit>

    suspend fun update(record: RecordEntity): Result<Unit>

    suspend fun getById(id: String): Result<RecordEntity?>

    suspend fun getRecordsCount(): Result<Long>

    suspend fun removeRecord(recordId: String): Result<Unit>

    suspend fun removeRecords(): Result<Unit>

    suspend fun getWordsCount(): Result<Long>
}
