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

    val res = liveData {
        listOf("One", "Two", "Three").forEach {
            emit("next value: ")
            emit(it)
        }
//        emit(repository.getRecords())
    }

    init {
//        viewModelScope.launch {
//            recordsRepository.records
//                .catch { exception -> Timber.e("$exception") }
//                .collect {
//                    _records.value = it
//                }
//        }
    }

    fun saveRecord() {
        val currentTitle = title.value
        val currentDescription = description.value

        if (currentTitle == null || currentDescription == null) {
            //_snackbarText.value = Event(R.string.empty_task_message)
            statusMessage.value = Event("Title and Desc cannot be empty")
            return
        }

        createRecord(RecordEntity(currentTitle, currentDescription))
    }

    private fun createRecord(newRecord: RecordEntity) = viewModelScope.launch {
        recordsRepository.insert(newRecord)

        _saveRecordComplete.value = true
        //tasksRepository.saveTask(newTask)
        //_taskUpdatedEvent.value = Event(Unit)
    }

    fun doneNavigation() {
        _saveRecordComplete.value = false
    }
}