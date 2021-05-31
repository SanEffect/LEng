package com.san.leng.ui

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.san.domain.entities.RecordEntity
import com.san.domain.entities.WordDefinition
import com.san.leng.R
import com.san.leng.core.utils.convertLongToDate
import com.san.leng.ui.dictionary.DictionaryAdapter
import com.san.leng.ui.records.RecordsAdapter
import timber.log.Timber

/**
 * Binding adapter used to hide the spinner once data is available
 */
@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("app:items")
fun bindItems(listView: RecyclerView, items: List<RecordEntity>?) {
    items?.let {
        (listView.adapter as RecordsAdapter).submitList(items)
    }
}

@BindingAdapter("app:recordsCount")
fun bindRecordsCount(textView: TextView, count: Long) {
    val resources = textView.context.resources
    textView.text = resources.getString(R.string.records_count_label, count)
}

@BindingAdapter("app:wordsCount")
fun bindWordsCount(textView: TextView, count: Long) {
    val resources = textView.context.resources
    textView.text = resources.getString(R.string.words_count_label, count)
}

@BindingAdapter("app:wordValue")
fun bindWordValue(textView: TextView, value: String?) {
    value?.let {
        val resources = textView.context.resources
        textView.text = resources.getString(R.string.word_value, value)
    }
}

@BindingAdapter("app:listDefinitions")
fun bindWordDefinition(listView: RecyclerView, wordDefinitions: List<WordDefinition>?) {
    wordDefinitions?.let { it ->
        (listView.adapter as DictionaryAdapter).submitList(it)
    }
}

@BindingAdapter("app:recordDate")
fun bindRecordDate(textView: TextView, creationDate: Long?) {
    creationDate.let {
        textView.text = convertLongToDate(creationDate!!)
    }
}
