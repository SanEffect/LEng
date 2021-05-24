package com.san.domain.repositories

import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import kotlinx.coroutines.flow.Flow

interface IRecordsRepository {

    //fun observeRecords(): LiveData<Result<List<DiaryRecord>>>

    val records: Flow<List<RecordEntity>>

    suspend fun getRecords(): Result<List<RecordEntity>>

//    fun observeRecords(): LiveData<Result<List<DiaryRecord>>>

    suspend fun getLastRecord() : Result<RecordEntity?>

    suspend fun insert(record: RecordEntity)

    suspend fun getById(id: Long) : Result<RecordEntity?>

    suspend fun removeRecords() : Result<Unit>

    //suspend fun getRecords(forceUpdate: Boolean = false): Result<List<DiaryRecord>>

    suspend fun refreshRecords()

    suspend fun getRecordsCount() : Result<Long>

    suspend fun getWordsCount() : Result<Long>

//    fun observeRecord(recordId: String): LiveData<Result<DiaryRecord>>
//
//    suspend fun getRecord(recordId: String, forceUpdate: Boolean = false): Result<DiaryRecord>
//
//    suspend fun refreshRecord(recordId: String)
//
//    suspend fun saveRecord(record: DiaryRecord)
//
//    suspend fun completeRecord(record: DiaryRecord)
//
//    suspend fun completeRecord(recordId: String)
//
//    suspend fun activateRecord(record: DiaryRecord)
//
//    suspend fun activateRecord(recordId: String)
//
//    suspend fun clearCompletedRecords()
//
//    suspend fun deleteAllRecords()
//
//    suspend fun deleteRecord(recordId: String)
}