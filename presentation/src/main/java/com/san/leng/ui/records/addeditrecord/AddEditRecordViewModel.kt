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
import com.san.leng.core.utils.convertDateToLong
import com.san.leng.core.utils.convertLongToDate
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class AddEditRecordViewModel @Inject constructor(
    private val getRecordUseCase: GetRecordUseCase,
    private val saveRecordUseCase: SaveRecordUseCase,
    private val getWordDefinitionsUseCase: GetWordDefinitionsUseCase
) : ViewModel() {

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val recordDate = MutableLiveData<String>()
    var dateInMillis: Long = System.currentTimeMillis()

//    private val _dataLoading = MutableLiveData<Boolean>()
//    val dataLoading: LiveData<Boolean> = _dataLoading

    init {
        recordDate.value = convertLongToDate(dateInMillis)
    }

    private val _saveRecordComplete = MutableLiveData<Event<Int>>()
    val saveRecordComplete: LiveData<Event<Int>> = _saveRecordComplete

    private val _datePickerClicked = MutableLiveData<Event<Boolean>>()
    val datePickerClicked: LiveData<Event<Boolean>> = _datePickerClicked

    private val _wordDefinition = MutableLiveData<Event<String>>()
    val wordDefinition: LiveData<Event<String>> = _wordDefinition

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private var recordId: String? = null

    private var isNewRecord: Boolean = false

    fun init(recordId: String?) {

        this.recordId = recordId
        if (recordId == null) {
            isNewRecord = true
            return
        }

        isNewRecord = false
        viewModelScope.launch {
            getRecordUseCase(GetRecordUseCase.Params(recordId)).let {
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
            recordDate.value = convertLongToDate(it.creationDate)
        }
    }

    fun saveRecord() {
        val currentTitle = title.value?.trim()
        val currentDescription = description.value?.trim()

        if (currentTitle.isNullOrEmpty() || currentDescription.isNullOrEmpty()) {
            _snackbarText.value = Event(R.string.empty_record_message)
            return
        }

        if (isNewRecord && recordId == null) {
            createRecord(RecordEntity(currentTitle, currentDescription, dateInMillis, false))
        } else {
            updateRecord(
                RecordEntity(
                    currentTitle,
                    currentDescription,
                    dateInMillis,
                    false,
                    recordId!!
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

    fun datePickerClicked() {
        _datePickerClicked.value = Event(true)
    }

    fun setDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val calendar: Calendar =
            GregorianCalendar(year, monthOfYear, dayOfMonth)

        dateInMillis = calendar.timeInMillis

        recordDate.value = convertLongToDate(dateInMillis)
    }
}
