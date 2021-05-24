package com.san.leng.ui.dashboard

import androidx.lifecycle.*
import com.san.domain.Result.Success
import com.san.domain.interactor.UseCase.None
import com.san.domain.usecases.dashboard.GetRecordsCount
import com.san.domain.usecases.records.GetWordsCount
import com.san.leng.core.platform.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val getRecordsCount: GetRecordsCount,
    private val getWordsCount: GetWordsCount
) : BaseViewModel() {

    private val _recordsCount: MutableLiveData<Long> = MutableLiveData()
    val recordsCount: LiveData<Long> = _recordsCount

    private val _wordsCount: MutableLiveData<Long> = MutableLiveData()
    val wordsCount: LiveData<Long> = _wordsCount

    fun loadRecordsCount() = getRecordsCount(None()) {
        when (it) {
            is Success -> _recordsCount.value = it.data
            is Error -> {
            }
        }
    }

    fun loadWordsCount() = getWordsCount(None()) {
        when (it) {
            is Success -> _wordsCount.value = it.data
            is Error -> {
            }
        }
    }

/*    val recordsCount = liveData {
        when(val result = getRecordsCount()) {
            is Success -> emit(result.data)
            is Error -> {}
        }
    }

    val wordsCount = liveData {
        when(val result = getWordsCount()) {
            is Success -> {
                Timber.i("wordsCount: ${result.data}")
                emit(result.data)
            }
            is Error -> {}
        }
    }*/
}