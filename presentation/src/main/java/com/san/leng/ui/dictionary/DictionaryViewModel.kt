package com.san.leng.ui.dictionary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.san.domain.usecases.dictionary.GetWordDefinitions
import com.san.domain.usecases.dictionary.GetWordDefinitions.Params
import com.san.leng.core.platform.BaseViewModel
import javax.inject.Inject
import com.san.domain.Result
import com.san.domain.Result.Success
import com.san.domain.Result.Error
import com.san.domain.entities.WordResult
import timber.log.Timber

class DictionaryViewModel @Inject constructor(
        private val getWordDefinitions: GetWordDefinitions
) : BaseViewModel() {

    private val _wordResult: MutableLiveData<WordResult> = MutableLiveData()
    val wordResult: LiveData<WordResult> = _wordResult

    fun getWordDefinition(word: String) = getWordDefinitions(Params(word)) {
        when(it) {
            is Success -> wordDisplay(it.data)
            is Error -> showError(it.exception)
            else -> showLoading()
        }
    }

    private fun showLoading() {
    }

    private fun wordDisplay(result: WordResult) {
        _wordResult.value = result
    }

    private fun showError(exception: Exception) {
        Timber.i( "Error: ${exception.message}")
    }

}