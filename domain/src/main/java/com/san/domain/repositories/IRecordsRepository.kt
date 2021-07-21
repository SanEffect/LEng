package com.san.domain.repositories

import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import kotlinx.coroutines.flow.Flow

interface IRecordsRepository {

    val records: Flow<List<RecordEntity>>

    suspend fun getRecords(forceUpdate: Boolean): Result<List<RecordEntity>>

    suspend fun getLastRecord(): Result<RecordEntity?>

    suspend fun saveRecord(record: RecordEntity)

    suspend fun getRecordById(id: String, forceUpdate: Boolean): Result<RecordEntity?>

    suspend fun removeRecord(recordId: String): Result<Unit>

    suspend fun removeRecords(): Result<Unit>

    suspend fun deleteRecords(recordIds: List<String>): Result<Unit>

    suspend fun refreshRecords()

    suspend fun getRecordsCount(): Result<Long>

    suspend fun getWordsCount(): Result<Long>
}
