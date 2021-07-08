package com.san.leng.ui.records.add_record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    var currentRecord: RecordEntity = RecordEntity()

    var recordDate = MutableLiveData<String>()

    private val _saveRecordComplete = MutableLiveData<Event<Boolean>>()
    val saveRecordComplete: LiveData<Event<Boolean>> = _saveRecordComplete

    private val _datePickerClicked = MutableLiveData<Event<Boolean>>()
    val datePickerClicked: LiveData<Event<Boolean>> = _datePickerClicked

    private val _warningMessage = MutableLiveData<Event<String>>()
    val warningMessage: LiveData<Event<String>> = _warningMessage

    private val _wordDefinition = MutableLiveData<Event<String>>()
    val wordDefinition: LiveData<Event<String>> = _wordDefinition

    fun saveRecord() {
        val currentTitle = currentRecord.title.trim()
        val currentDescription = currentRecord.description.trim()

        if (currentTitle.isEmpty() || currentDescription.isEmpty()) {
            _warningMessage.value = Event("Title and Description cannot be empty")
            return
        }

        createRecord(CreateRecord.Params(currentRecord))
        _saveRecordComplete.value = Event(true)
    }

    fun getWordDefinition(word: String?) = viewModelScope.launch {

        word?.let {
            getWordDefinitions(GetWordDefinitions.Params(word)) {
                when (it) {
                    is Success -> {
                        // TODO: temporary solution
                        _wordDefinition.value = Event(it.data.definition)
                    }
                    is Error -> {
                        Timber.i("getWordDefinitions error: ${it.exception.message}")
                    }
                }
            }
        }

    }

    fun datePickerClicked() {
        _datePickerClicked.value = Event(true)
    }

    fun setDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        recordDate.value = "$dayOfMonth.$monthOfYear.$year"
    }
}
