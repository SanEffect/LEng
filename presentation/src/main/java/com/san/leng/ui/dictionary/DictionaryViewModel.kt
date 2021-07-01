package com.san.leng.ui.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.san.domain.usecases.dictionary.GetWordDefinitions
import com.san.domain.usecases.dictionary.GetWordDefinitions.Params
import com.san.leng.core.platform.BaseViewModel
import javax.inject.Inject
import com.san.domain.Result.Success
import com.san.domain.Result.Error
import com.san.domain.models.WordResponse
import com.san.domain.usecases.dictionary.GetWord
import com.san.leng.core.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class DictionaryViewModel @Inject constructor(
        private val getWord: GetWord,
        private val getWordDefinitions: GetWordDefinitions
) : BaseViewModel() {

    var errorMessage: String? = null

    private val _errorMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val errorMessages: LiveData<Event<String>> = _errorMessage

    private val _wordResponse: MutableLiveData<WordResponse> = MutableLiveData()
    val wordResponse: LiveData<WordResponse> = _wordResponse

    private val _wordResultIsLoaded: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val wordResultIsLoaded: LiveData<Event<Boolean>> = _wordResultIsLoaded

    fun loadWord(word: String) = viewModelScope.launch {
        getWord(GetWord.Params((word))) {

            _wordResultIsLoaded.value = Event(true)

            when(it) {
                is Success -> {
                    val res = it.data
//                    Timber.i("wordResult: ${res}")
                    wordDisplay(it.data)
                }
                is Error -> showError(it.exception)
                else -> showLoading()
            }
        }
    }

    fun loadWordDefinition(word: String) = viewModelScope.launch {

        getWordDefinitions(Params((word))) {

            _wordResultIsLoaded.value = Event(true)

            Timber.i("getWordDefinitions it: ${it}")

            when(it) {
                is Success -> {
                    val res = it.data
                    Timber.i("wordResult: ${res}")
//                    wordDisplay(it.data)
                }
                is Error -> showError(it.exception)
                else -> showLoading()
            }
        }
    }

    private fun showLoading() {
    }

    private fun wordDisplay(response: WordResponse) {
        _wordResponse.value = response
    }

    private fun showError(exception: Exception) {
        errorMessage = exception.message
        _errorMessage.value = Event(exception.message.toString())
        Timber.i( "Error: ${exception.message}")
    }

}
