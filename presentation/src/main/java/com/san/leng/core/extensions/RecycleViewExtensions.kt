package com.san.leng.core.extensions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.autoFitColumns(columnWidth: Int) {
    val displayMetrics = this.context.resources.displayMetrics
    val noOfColumns =
        ((displayMetrics.widthPixels / displayMetrics.density) / columnWidth).toInt()
    this.layoutManager = GridLayoutManager(this.context, noOfColumns)
}
