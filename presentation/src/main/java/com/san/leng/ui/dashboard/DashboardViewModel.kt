package com.san.leng.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.san.domain.Result.Success
import com.san.domain.usecases.dashboard.GetRecordsCountUseCase
import com.san.domain.usecases.records.GetWordsCountUseCase
import com.san.leng.core.platform.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val getRecordsCountUseCase: GetRecordsCountUseCase,
    private val getWordsCountUseCase: GetWordsCountUseCase
) : BaseViewModel() {

    private val _recordsCount: MutableLiveData<Long> = MutableLiveData()
    val recordsCount: LiveData<Long> = _recordsCount

    private val _wordsCount: MutableLiveData<Long> = MutableLiveData()
    val wordsCount: LiveData<Long> = _wordsCount

    fun loadRecordsCount() = viewModelScope.launch {

        when (val result = getRecordsCountUseCase(Unit)) {
            is Success -> _recordsCount.value = result.data
            is Error -> {
            }
        }
    }

    fun loadWordsCount() = viewModelScope.launch {

        when (val result = getWordsCountUseCase(Unit)) {
            is Success -> _wordsCount.value = result.data
            is Error -> {
            }
        }
    }
}
