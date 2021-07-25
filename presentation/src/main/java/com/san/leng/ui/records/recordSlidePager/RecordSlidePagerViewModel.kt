package com.san.leng.ui.records.recordSlidePager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import com.san.domain.usecases.records.GetRecordsUseCase
import com.san.leng.core.Event
import com.san.leng.core.di.scopes.RecordsScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class RecordSlidePagerViewModel @Inject constructor(
    private val getRecordsUseCase: GetRecordsUseCase,
) : ViewModel() {

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val backgroundColor = MutableLiveData<String?>()
    val creationDate = MutableLiveData(System.currentTimeMillis())

    private val _records: MutableLiveData<List<RecordEntity>> = MutableLiveData()
    val records: LiveData<List<RecordEntity>> = _records

    private val _navigateBack = MutableLiveData<Event<Boolean>>()
    val navigateBack: LiveData<Event<Boolean>> = _navigateBack

    init {
        loadRecords()
    }

    fun loadRecords() = viewModelScope.launch {

        when (val result = getRecordsUseCase(GetRecordsUseCase.Params(false))) {
            is Result.Success -> {
                val records = result.data

                _records.value = records
            }
            is Result.Failure -> {
            }
        }
    }

    fun editRecord() {

    }

    fun getPrevRecord() {

    }

    fun getNextRecord() {

    }

    fun setBackgroundColor(value: String?) {
        backgroundColor.value = value
    }

    fun navigateBack() {
        _navigateBack.value = Event(true)
    }
}
