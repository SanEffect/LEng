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

    fun loadRecords() = getRecords(None()) {

        when(it) {
            is Success -> _records.value = it.data
            is Error -> {}
            else -> {}
        }
    }

//    fun loadRecords() = viewModelScope.launch {
//
//
//
//        when(val records = getRecords()) {
//            is Success -> {
//                _records.value = records.data
//            }
//            is Error -> {
//
//            }
//            else -> {}
//        }
//    }

//    val records = liveData {
//
////        val records = listOf<RecordEntity>(
////                RecordEntity("Rec 1", "Desc"),
////                RecordEntity("Rec 2", "Desc"),
////                RecordEntity("Rec 3", "Desc")
////        )
////        emit(records)
//
//        when (val records = recordsRepository.getRecords()) {
//            is Success -> emit(records.data)
//            is Error -> Timber.i("Error when try getting records")
//        }
//    }

    fun clearRecords() = viewModelScope.launch {
//        removeRecords()
    }
}