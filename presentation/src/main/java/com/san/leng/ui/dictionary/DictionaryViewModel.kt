package com.san.leng.ui.dictionary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.san.domain.usecases.dictionary.GetWordDefinitions
import com.san.domain.usecases.dictionary.GetWordDefinitions.Params
import com.san.leng.core.platform.BaseViewModel
import javax.inject.Inject
import com.san.domain.Result
import com.san.domain.Result.Success
import com.san.domain.Result.Error
import com.san.domain.entities.WordResult
import com.san.leng.core.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class DictionaryViewModel @Inject constructor(
        private val getWordDefinitions: GetWordDefinitions
) : BaseViewModel() {

    var errorMessage: String? = null

    private val _errorMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val errorMessages: LiveData<Event<String>> = _errorMessage

    private val _wordResult: MutableLiveData<WordResult> = MutableLiveData()
    val wordResult: LiveData<WordResult> = _wordResult

    private val _wordResultIsLoaded: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val wordResultIsLoaded: LiveData<Event<Boolean>> = _wordResultIsLoaded

    fun loadWordDefinition(word: String) = viewModelScope.launch {

        getWordDefinitions(Params((word))) {

            _wordResultIsLoaded.value = Event(true)

            when(it) {
                is Success -> wordDisplay(it.data)
                is Error -> showError(it.exception)
                else -> showLoading()
            }
        }
    }

    private fun showLoading() {
    }

    private fun wordDisplay(result: WordResult) {
        _wordResult.value = result
    }

    private fun showError(exception: Exception) {
        errorMessage = exception.message
        _errorMessage.value = Event(exception.message.toString())
        Timber.i( "Error: ${exception.message}")
    }

}