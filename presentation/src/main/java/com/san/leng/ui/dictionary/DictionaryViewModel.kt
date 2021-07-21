package com.san.leng.ui.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.san.domain.Result.Failure
import com.san.domain.Result.Success
import com.san.domain.models.WordResponse
import com.san.domain.usecases.dictionary.GetWordDefinitionsUseCase
import com.san.domain.usecases.dictionary.GetWordDefinitionsUseCase.Params
import com.san.domain.usecases.dictionary.GetWordUseCase
import com.san.leng.core.Event
import com.san.leng.core.platform.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class DictionaryViewModel @Inject constructor(
    private val getWordUseCase: GetWordUseCase,
    private val getWordDefinitionsUseCase: GetWordDefinitionsUseCase,
) : BaseViewModel() {

    var errorMessage: String? = null

    private val _errorMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val errorMessages: LiveData<Event<String>> = _errorMessage

    private val _wordResponse: MutableLiveData<WordResponse> = MutableLiveData()
    val wordResponse: LiveData<WordResponse> = _wordResponse

    private val _wordResultIsLoaded: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val wordResultIsLoaded: LiveData<Event<Boolean>> = _wordResultIsLoaded

    fun loadWord(word: String) = viewModelScope.launch {

        when (val result = getWordUseCase(GetWordUseCase.Params(word))) {
            is Success -> wordDisplay(result.data)
            is Error -> {
            }
        }
    }

    fun loadWordDefinition(word: String) = viewModelScope.launch {

        val result = getWordDefinitionsUseCase(Params((word)))

        _wordResultIsLoaded.value = Event(true)

//            Timber.i("getWordDefinitions it: $result")

        when (result) {
            is Success -> {
                val res = result.data
                Timber.i("wordResult: $res")
//                    wordDisplay(it.data)
            }
            is Failure -> showError(result.exception)
            else -> showLoading()
        }

    }

    private fun showLoading() {
    }

    private fun wordDisplay(word: WordResponse?) {
        word?.let {
            _wordResponse.value = it
            _wordResultIsLoaded.value = Event(true)
        }
    }

    private fun showError(exception: Exception) {
        errorMessage = exception.message
        _errorMessage.value = Event(exception.message.toString())
        Timber.i("Error: ${exception.message}")
    }

}
