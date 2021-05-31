package com.san.leng.ui.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.san.domain.Result.Success
import com.san.domain.Result.Error
import com.san.domain.entities.RecordEntity
import com.san.domain.interactor.UseCase.None
import com.san.domain.usecases.records.GetRecords
import com.san.domain.usecases.records.RemoveRecords
import com.san.leng.core.platform.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecordsViewModel @Inject constructor(
    private val getRecords: GetRecords,
    private val removeRecords: RemoveRecords
) : BaseViewModel() {

    private val _records: MutableLiveData<List<RecordEntity>> = MutableLiveData()
    val records: LiveData<List<RecordEntity>> = _records

    fun loadRecords() = viewModelScope.launch {
        getRecords(None()) {
            when(it) {
                is Success -> _records.value = it.data
                is Error -> {}
                else -> {}
            }
        }
    }

    fun clearRecords() = viewModelScope.launch {
        // removeRecords()
    }

    fun removeRecord() = viewModelScope.launch {
        // TODO: removeRecord()
    }
}