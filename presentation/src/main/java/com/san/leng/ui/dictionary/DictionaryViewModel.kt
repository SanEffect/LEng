package com.san.leng.ui.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.san.domain.Result.Error
import com.san.domain.Result.Success
import com.san.domain.models.WordResponse
import com.san.domain.usecases.dictionary.GetWord
import com.san.domain.usecases.dictionary.GetWordDefinitions
import com.san.domain.usecases.dictionary.GetWordDefinitions.Params
import com.san.domain.usecases.dictionary.GetWordFromDatabase
import com.san.domain.usecases.dictionary.InsertWord
import com.san.leng.core.Event
import com.san.leng.core.platform.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class DictionaryViewModel @Inject constructor(
    private val getWord: GetWord,
    private val getWordFromDB: GetWordFromDatabase,
    private val getWordDefinitions: GetWordDefinitions,
    private val insertWord: InsertWord
) : BaseViewModel() {

    var errorMessage: String? = null

    private val _errorMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val errorMessages: LiveData<Event<String>> = _errorMessage

    private val _wordResponse: MutableLiveData<WordResponse> = MutableLiveData()
    val wordResponse: LiveData<WordResponse> = _wordResponse

    private val _wordResultIsLoaded: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val wordResultIsLoaded: LiveData<Event<Boolean>> = _wordResultIsLoaded

    fun loadWord(word: String) = viewModelScope.launch {
//        getWord(GetWord.Params((word))) {
//
//            _wordResultIsLoaded.value = Event(true)
//
//            when(it) {
//                is Success -> {
//                    val res = it.data
//                    Timber.i("loadWord result: $res")
//                    wordDisplay(it.data)
//                }
//                is Error -> showError(it.exception)
//                else -> showLoading()
//            }
//        }

        getWordFromDB(GetWordFromDatabase.Params((word))) { result ->

            when (result) {
                is Success -> {
                    Timber.i("--------- getWordFromDB Success: ${result.data}")
                    wordDisplay(result.data.toDomain())
                }
                is Error -> {

                    Timber.i("--------- GetWordFromDB Error! Try to get by Network ---------------")

                    getWord(GetWord.Params((word))) { response ->

                        when (response) {
                            is Success -> {
                                wordDisplay(response.data)
                                // save into db
                                insertWord(InsertWord.Params(response.data.toEntity()))
                            }
                            is Error -> {
                                // show error
                                Timber.i("getWord error -----------------------")
                            }
                        }
                    }
                }
            }
        }
    }

    fun loadWordDefinition(word: String) = viewModelScope.launch {

        getWordDefinitions(Params((word))) {

            _wordResultIsLoaded.value = Event(true)

            Timber.i("getWordDefinitions it: ${it}")

            when (it) {
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

        _wordResultIsLoaded.value = Event(true)
        _wordResponse.value = response
    }

    private fun showError(exception: Exception) {
        errorMessage = exception.message
        _errorMessage.value = Event(exception.message.toString())
        Timber.i("Error: ${exception.message}")
    }

}
