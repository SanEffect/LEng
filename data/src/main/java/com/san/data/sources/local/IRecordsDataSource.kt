package com.san.data.sources.local

import com.san.domain.entities.RecordEntity
import kotlinx.coroutines.flow.Flow
import com.san.domain.Result

interface IRecordsDataSource {

    val records: Flow<List<RecordEntity>>

    suspend fun getRecords(): Result<List<RecordEntity>>

    suspend fun getLastRecord() : Result<RecordEntity>

    suspend fun insert(record: RecordEntity)

    suspend fun getById(id: Long) : Result<RecordEntity?>

    suspend fun getRecordsCount() : Result<Long>

    suspend fun removeRecords() : Result<Unit>

    suspend fun getWordsCount() : Result<Long>
}