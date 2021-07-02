package com.san.leng.core.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertLongToDate(time: Long, format: String = "dd.MM.yy"): String {
    val date = Date(time)
    val format = SimpleDateFormat(format)
    return format.format(date)
}

fun currentTimeToLong(): Long {
    return System.currentTimeMillis()
}

fun convertDateToLong(date: String, format: String = "dd.MM.yy"): Long {
    val df = SimpleDateFormat(format)
    return df.parse(date).time
}

fun getWordIndexBySelectionStart(match: MatchResult?, selectionStart: Int): Int? {
    return if (match?.range?.contains(selectionStart) == false) getWordIndexBySelectionStart(
        match.next(),
        selectionStart
    ) else match?.range?.first
}

