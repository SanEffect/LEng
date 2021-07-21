package com.san.leng.ui.dictionary

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.san.domain.models.WordResult
import com.san.leng.R
import timber.log.Timber

@BindingAdapter("app:wordDefinition")
fun bindWordDefinition(textView: TextView, wordResult: WordResult?) {
//    Timber.i("wordResult: $wordResult")
//
    Timber.i("def: ${wordResult?.definition}")

    textView.text = wordResult?.definition

//    wordResult?.results?.let {
//        val defs = it.map { it.definition }.toString()
//        Timber.i("defs: $defs")
//        textView.text = defs
//    }
}


@BindingAdapter("app:wordValue")
fun bindWordValue(textView: TextView, value: String?) {
    value?.let {
        val resources = textView.context.resources
        textView.text = resources.getString(R.string.word_value, value)
    }
}

@BindingAdapter("app:wordSynonyms")
fun bindWordSynonyms(textView: TextView, wordResult: WordResult?) {
    wordResult?.let {
        textView.text = it.synonyms?.joinToString()
    }
}

@BindingAdapter("app:listDefinitions")
fun bindWordResult(listView: RecyclerView, wordResults: List<WordResult>?) {
    wordResults?.let { it ->
        val definitions = wordResults.map { it.definition }
        (listView.adapter as DictionaryAdapter).submitList(wordResults)
    }
}
