package com.san.leng.ui

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.san.domain.entities.*
import com.san.domain.models.WordResult
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

@BindingAdapter("app:records")
fun bindItems(listView: RecyclerView, records: List<RecordEntity>?) {
    records?.let {
        (listView.adapter as RecordsAdapter).submitRecordList(records)
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
fun bindWordResult(listView: RecyclerView, wordResults: List<WordResult>?) {
    wordResults?.let { it ->
        val definitions = wordResults.map { it.definition }
        (listView.adapter as DictionaryAdapter).submitList(wordResults)
    }
}

@BindingAdapter("app:recordDate")
fun bindRecordDate(textView: TextView, creationDate: Long?) {
    creationDate.let {
        textView.text = convertLongToDate(creationDate!!)
    }
}

@BindingAdapter("app:wordDefinition")
fun bindWordDefinition(textView: TextView, wordResult: WordResult?) {
    Timber.i("wordResult: $wordResult")

    Timber.i("def: ${wordResult?.definition}")

    textView.text = wordResult?.definition

//    wordResult?.results?.let {
//        val defs = it.map { it.definition }.toString()
//        Timber.i("defs: $defs")
//        textView.text = defs
//    }
}

@BindingAdapter("app:wordSynonyms")
fun bindWordSynonyms(textView: TextView, wordResult: WordResult?) {
    wordResult?.let {
        textView.text = it.synonyms?.joinToString()
    }
}

//@BindingAdapter("app:setupBottomNav")
//fun bindIsRecordsFragment(bottomNavigationView: BottomNavigationView, params: String) {
//    when(bottomNavigationView.selectedItemId) {
//        R.id.recordsFragment -> {
//            Timber.i("IS FRAGMENT_RECORDS ----------------------------------")
//        }
//        else -> {}
//    }
//}


//@BindingMethods(BindingMethod(
//        type = BottomNavigationView::class,
//        attribute = "app:onNavigationItemSelected",
//        method = "setOnNavigationItemSelectedListener"))
//class DataBindingAdapter
