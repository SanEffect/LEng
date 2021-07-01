package com.san.data.sources.local

import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import kotlinx.coroutines.flow.Flow

interface IRecordsDataSource {

    val records: Flow<List<RecordEntity>>

    suspend fun getRecords(): Result<List<RecordEntity>>

    suspend fun getLastRecord(): Result<RecordEntity>

    suspend fun insert(record: RecordEntity): Result<Unit>

    suspend fun update(record: RecordEntity): Result<Unit>

    suspend fun getById(id: Long): Result<RecordEntity?>

    suspend fun getRecordsCount(): Result<Long>

    suspend fun removeRecord(recordId: Long): Result<Unit>

    suspend fun removeRecords(): Result<Unit>

    suspend fun getWordsCount(): Result<Long>
}
