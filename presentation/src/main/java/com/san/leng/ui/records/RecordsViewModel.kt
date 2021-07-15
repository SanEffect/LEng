package com.san.leng.ui.records

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.san.domain.Result.Error
import com.san.domain.Result.Success
import com.san.domain.entities.RecordEntity
import com.san.domain.usecases.records.GetRecordsUseCase
import com.san.domain.usecases.records.RemoveRecordUseCase
import com.san.domain.usecases.records.RemoveRecordsUseCase
import com.san.leng.R
import com.san.leng.core.Constants.RECORD_ADD_RESULT_OK
import com.san.leng.core.Constants.RECORD_DELETE_RESULT_OK
import com.san.leng.core.Constants.RECORD_EDIT_RESULT_OK
import com.san.leng.core.Event
import com.san.leng.core.platform.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecordsViewModel @Inject constructor(
    private val getRecordsUseCase: GetRecordsUseCase,
    private val removeRecordUseCase: RemoveRecordUseCase,
    private val removeRecordsUseCase: RemoveRecordsUseCase
) : BaseViewModel() {

    val selectableMode = ObservableBoolean(false)

    private val _records: MutableLiveData<List<RecordEntity>> = MutableLiveData()
    val records: LiveData<List<RecordEntity>> = _records

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

//    val hasRecords = Transformations.map(_records) {
//        it.isNotEmpty()
//    }

    fun loadRecords() = viewModelScope.launch {

        when (val result = getRecordsUseCase(GetRecordsUseCase.Params(false))) {
            is Success -> {
                _records.value = result.data
            }
            is Error -> showSnackbarMessage(R.string.failure_load_records)
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

    fun changeSelectableMode(state: Boolean) {
        selectableMode.set(state)
    }
}
