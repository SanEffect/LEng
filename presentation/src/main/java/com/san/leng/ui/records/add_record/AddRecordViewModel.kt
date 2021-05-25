package com.san.leng.ui.records.add_record

import androidx.lifecycle.*
import com.san.domain.entities.RecordEntity
import com.san.domain.repositories.IRecordsRepository
import com.san.leng.core.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddRecordViewModel @Inject constructor(
    private val recordsRepository: IRecordsRepository
) : ViewModel() {

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    private val _saveRecordComplete = MutableLiveData<Boolean>()
    val saveRecordComplete: LiveData<Boolean> = _saveRecordComplete

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    private val _records = MutableLiveData<List<RecordEntity>>()
    val records: LiveData<List<RecordEntity>> = _records

    fun saveRecord() {
        val currentTitle = title.value
        val currentDescription = description.value

        if (currentTitle == null || currentDescription == null) {
            statusMessage.value = Event("Title and Description cannot be empty")
            return
        }

        createRecord(RecordEntity(currentTitle, currentDescription))
    }

    private fun createRecord(newRecord: RecordEntity) = viewModelScope.launch {
        recordsRepository.insert(newRecord)

        _saveRecordComplete.value = true
    }

    fun doneNavigation() {
        _saveRecordComplete.value = false
    }
}