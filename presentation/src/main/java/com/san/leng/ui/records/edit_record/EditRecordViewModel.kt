package com.san.leng.ui.records.edit_record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.domain.Result
import com.san.domain.entities.RecordEntity
import com.san.domain.usecases.dictionary.GetWordDefinitions
import com.san.domain.usecases.records.UpdateRecord
import com.san.leng.core.Event
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class EditRecordViewModel @Inject constructor(
    private val updateRecord: UpdateRecord,
    private val getWordDefinitions: GetWordDefinitions
) : ViewModel() {

    var currentRecord: RecordEntity = RecordEntity()

    private val _updateRecordComplete = MutableLiveData<Event<Boolean>>()
    val updateRecordComplete: LiveData<Event<Boolean>> = _updateRecordComplete

    private val _warningMessage = MutableLiveData<Event<String>>()
    val warningMessage: LiveData<Event<String>> = _warningMessage

    private val _wordDefinition = MutableLiveData<Event<String>>()
    val wordDefinition: LiveData<Event<String>> = _wordDefinition

    fun updateRecord() {
        val currentTitle = currentRecord.title.trim()
        val currentDescription = currentRecord.description.trim()

        if (currentTitle.isEmpty() || currentDescription.isEmpty()) {
            _warningMessage.value = Event("Title and Description cannot be empty")
            return
        }

        updateRecord(UpdateRecord.Params(currentRecord))
        _updateRecordComplete.value = Event(true)
    }

    fun getWordDefinition(word: String?) = viewModelScope.launch {

        word?.let {
            getWordDefinitions(GetWordDefinitions.Params(word)) {
                when (it) {
                    is Result.Success -> {
                        // TODO: temporary solution
                        Timber.i("Definition: ${it.data.definition} <--------------------------------")
                        _wordDefinition.value = Event(it.data.definition)
                    }
                    is Result.Error -> {
                        Timber.i("getWordDefinitions error: ${it.exception.message}")
                    }
                }
            }
        }
    }
}
