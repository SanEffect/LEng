package com.san.leng.ui.statistics

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.san.leng.R

@BindingAdapter("app:wordsCount")
fun bindWordsCount(textView: TextView, count: Long) {
    val resources = textView.context.resources
    textView.text = resources.getString(R.string.words_count_label, count)
}
