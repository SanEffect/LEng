package com.san.leng.ui.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.san.domain.Result.Failure
import com.san.domain.Result.Success
import com.san.domain.entities.RecordEntity
import com.san.domain.usecases.records.DeleteRecordsUseCase
import com.san.domain.usecases.records.GetRecordsUseCase
import com.san.domain.usecases.records.RemoveRecordUseCase
import com.san.domain.usecases.records.RemoveRecordsUseCase
import com.san.leng.R
import com.san.leng.core.Constants.RECORD_ADD_RESULT_OK
import com.san.leng.core.Constants.RECORD_DELETE_RESULT_OK
import com.san.leng.core.Constants.RECORD_EDIT_RESULT_OK
import com.san.leng.core.Event
import com.san.leng.core.enums.CommonStatus
import com.san.leng.core.platform.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecordsViewModel @Inject constructor(
    private val getRecordsUseCase: GetRecordsUseCase,
    private val removeRecordUseCase: RemoveRecordUseCase,
    private val removeRecordsUseCase: RemoveRecordsUseCase,
    private val deleteRecords: DeleteRecordsUseCase,
) : BaseViewModel() {

    private var recordsOrderByAsc = false

    private val _status = MutableLiveData<CommonStatus>()
    val status: LiveData<CommonStatus> = _status

    val selectableMode = MutableLiveData(false)
    val isSelectableMode
        get() = selectableMode.value == true

    private val _records: MutableLiveData<List<RecordEntity>> = MutableLiveData()
    val records: LiveData<List<RecordEntity>> = _records

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    fun loadRecords() = viewModelScope.launch {

        _status.value = CommonStatus.LOADING

        when (val result = getRecordsUseCase(GetRecordsUseCase.Params(false))) {
            is Success -> {
                val records = result.data

                _status.value = if (records.isEmpty())
                    CommonStatus.EMPTY
                else
                    CommonStatus.SUCCESS

                _records.value = records
            }
            is Failure -> {
                _status.value = CommonStatus.ERROR
                showSnackbarMessage(R.string.failure_load_records)
            }
        }
    }

    fun switchRecordsOrder() {
        recordsOrderByAsc = !recordsOrderByAsc

        when (recordsOrderByAsc) {
            true -> _records.value = _records.value?.sortedBy { it.creationDate }
            false -> _records.value = _records.value?.sortedByDescending { it.creationDate }
        }
    }

    /**
     * Removing all records
     */
    fun clearRecords() = viewModelScope.launch {

        val result = removeRecordsUseCase(Unit)
        if (result !is Success) {
            showSnackbarMessage(R.string.failure_remove_records_message)
        }
    }

    /**
     * Record item removing
     */
    fun removeRecord(recordId: String) = viewModelScope.launch {

        val result = removeRecordUseCase(RemoveRecordUseCase.Params(recordId))
        if (result !is Success) {
            showSnackbarMessage(R.string.failure_remove_record_message)
        }
    }

    fun showResultMessage(result: Int) {
        when (result) {
            RECORD_EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_saved_record_message)
            RECORD_ADD_RESULT_OK -> showSnackbarMessage(R.string.successfully_added_record_message)
            RECORD_DELETE_RESULT_OK -> showSnackbarMessage(R.string.successfully_deleted_record_message)
        }
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

    fun setSelectableMode(state: Boolean) {
        selectableMode.value = state
    }

    fun deleteRecords(recordIds: List<String>) = viewModelScope.launch {
        deleteRecords(DeleteRecordsUseCase.Params(recordIds))
    }
}
