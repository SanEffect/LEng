package com.san.leng.ui.records.addeditrecord

import androidx.lifecycle.*
import com.san.domain.Result.Error
import com.san.domain.Result.Success
import com.san.domain.entities.RecordEntity
import com.san.domain.usecases.dictionary.GetWordDefinitionsUseCase
import com.san.domain.usecases.records.GetRecordUseCase
import com.san.domain.usecases.records.SaveRecordUseCase
import com.san.leng.R
import com.san.leng.core.Constants
import com.san.leng.core.Event
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddEditRecordViewModel @Inject constructor(
    private val getRecordUseCase: GetRecordUseCase,
    private val saveRecordUseCase: SaveRecordUseCase,
    private val getWordDefinitionsUseCase: GetWordDefinitionsUseCase
) : ViewModel() {

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val backgroundColor = MutableLiveData<String>()
    val creationDate = MutableLiveData(System.currentTimeMillis())

    var bgPickerExpanded = MutableLiveData(false)

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _saveRecordComplete = MutableLiveData<Event<Int>>()
    val saveRecordComplete: LiveData<Event<Int>> = _saveRecordComplete

    private val _wordDefinition = MutableLiveData<Event<String>>()
    val wordDefinition: LiveData<Event<String>> = _wordDefinition

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private var recordId: String? = null

    private var isNewRecord: Boolean = false

    fun init(recordId: String?, backgroundColor: String?) {
        if (_dataLoading.value == true) return

        this.recordId = recordId
        if (recordId == null) {
            isNewRecord = true
            return
        }

        backgroundColor?.let {
            setBackgroundColor(it)
        }

        isNewRecord = false
        _dataLoading.value = true
        viewModelScope.launch {
            getRecordUseCase(GetRecordUseCase.Params(recordId, false)).let {
                when (it) {
                    is Success -> onRecordLoaded(it.data)
                    is Error -> {
                        // show error
                    }
                }
            }
        }
    }

    private fun onRecordLoaded(recordEntity: RecordEntity?) {

        recordEntity?.let {
            title.value = it.title
            description.value = it.description
            creationDate.value = it.creationDate
        }

        _dataLoading.value = false
    }

    fun saveRecord() {
        val currentTitle = title.value?.trim()
        val currentDescription = description.value?.trim()

        if (currentTitle.isNullOrEmpty() || currentDescription.isNullOrEmpty()) {
            _snackbarText.value = Event(R.string.empty_record_message)
            return
        }

        if (isNewRecord && recordId == null) {
            createRecord(
                RecordEntity(
                    currentTitle,
                    currentDescription,
                    creationDate.value!!,
                    isDeleted = false,
                    isDraft = false,
                    backgroundColor = backgroundColor.value
                )
            )
        } else {
            updateRecord(
                RecordEntity(
                    currentTitle,
                    currentDescription,
                    creationDate.value!!,
                    isDeleted = false,
                    isDraft = false,
                    backgroundColor = backgroundColor.value,
                    id = recordId!!
                )
            )
        }
    }

    private fun createRecord(recordEntity: RecordEntity) = viewModelScope.launch {
        saveRecordUseCase(SaveRecordUseCase.Params(recordEntity))
        _saveRecordComplete.value = Event(Constants.RECORD_ADD_RESULT_OK)
    }

    private fun updateRecord(recordEntity: RecordEntity) = viewModelScope.launch {
        saveRecordUseCase(SaveRecordUseCase.Params(recordEntity))
        _saveRecordComplete.value = Event(Constants.RECORD_EDIT_RESULT_OK)
    }

    fun getWordDefinition(word: String?) = viewModelScope.launch {

        word?.let {
            /*getWordDefinitions(GetWordDefinitions.Params(word)) {
                when (it) {
                    is Success -> {
                        // TODO: temporary solution
                        _wordDefinition.value = Event(it.data.definition)
                    }
                    is Error -> {
                        Timber.i("getWordDefinitions error: ${it.exception.message}")
                    }
                }
            }*/
        }
    }

    fun setBackgroundColor(value: String) {
        backgroundColor.value = value
    }

    fun setDate(value: Long) {
        creationDate.value = value
//        creationDate.value = outputDateFormat.format(value)
    }

    private val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun loadBackgrounds(): LiveData<String> {
        return liveData {
            ""
        }
    }

    fun openBackgrounPicker() {
        bgPickerExpanded.value = true
    }

    fun closeBackgroundPicker() {
        bgPickerExpanded.value = false
    }
}
