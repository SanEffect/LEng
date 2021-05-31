package com.san.leng.ui.records.add_record

import androidx.lifecycle.*
import com.san.domain.Result.Error
import com.san.domain.Result.Success
import com.san.domain.entities.RecordEntity
import com.san.domain.usecases.dictionary.GetWordDefinitions
import com.san.domain.usecases.records.CreateRecord
import com.san.leng.core.Event
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AddRecordViewModel @Inject constructor(
    private val createRecord: CreateRecord,
    private val getWordDefinitions: GetWordDefinitions
) : ViewModel() {

    var isEditMode = false
    var currentRecord: RecordEntity? = null

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    private val _saveRecordComplete = MutableLiveData<Event<Boolean>>()
    val saveRecordComplete: LiveData<Event<Boolean>> = _saveRecordComplete

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = statusMessage

    private val _wordDefinition = MutableLiveData<Event<String>>()
    val wordDefinition: LiveData<Event<String>> = _wordDefinition

    private val _records = MutableLiveData<List<RecordEntity>>()
    val records: LiveData<List<RecordEntity>> = _records

    fun saveRecord() {
        val currentTitle = title.value?.trim()
        val currentDescription = description.value?.trim()

        if (currentTitle == null || currentDescription == null) {
            statusMessage.value = Event("Title and Description cannot be empty")
            return
        }

        val record = RecordEntity(currentTitle, currentDescription)

        currentRecord?.let {
            record.id = it.id
            record.creationDate = it.creationDate
            record.isDeleted = it.isDeleted
        }

        createRecord(CreateRecord.Params(record, update = isEditMode))
        _saveRecordComplete.value = Event(true)
    }

    fun getWordDefinition(word: String) = viewModelScope.launch {
        getWordDefinitions(GetWordDefinitions.Params(word)) {
            when(it) {
                is Success -> {
                    // TODO: temporary solution
                    val firstDefinition = it.data.definitions.first().definition
                    _wordDefinition.value = Event(firstDefinition)
                }
                is Error -> { Timber.i("Result is Error")}
                else -> { Timber.i("Result is undefined")}
            }
        }
    }
}