package com.san.leng.core.utils

import java.text.SimpleDateFormat
import java.util.*

object Converter {

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
}


