package com.san.leng.ui.records

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.san.domain.entities.RecordEntity
import com.san.leng.R
import com.san.leng.core.utils.convertLongToDate

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

@BindingAdapter("app:recordDate")
fun bindRecordDate(textView: TextView, creationDate: Long?) {
    creationDate.let {
        textView.text = convertLongToDate(creationDate!!)
    }
}
