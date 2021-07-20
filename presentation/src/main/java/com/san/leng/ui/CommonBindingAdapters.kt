package com.san.leng.ui

import android.graphics.Color
import android.view.View
import androidx.databinding.BindingAdapter
import com.san.leng.core.enums.CommonStatus
import com.san.leng.core.extensions.invisible
import com.san.leng.core.extensions.visible


/**
 * Binding adapter used to hide the spinner once data is available
 */
@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("showIfEmpty")
fun bindShowIfEmpty(view: View, status: CommonStatus) {
    when (status) {
        CommonStatus.EMPTY -> view.visible()
        else -> view.invisible()
    }
}

@BindingAdapter("showIfError")
fun bindShowIfError(view: View, status: CommonStatus) {
    when (status) {
        CommonStatus.ERROR -> view.visible()
        else -> view.invisible()
    }
}

@BindingAdapter("showIfSuccess")
fun bindShowIfSuccess(view: View, status: CommonStatus) {
    when (status) {
        CommonStatus.SUCCESS -> view.visible()
        else -> view.invisible()
    }
}

@BindingAdapter("showIfLoading")
fun bindShowIfLoading(view: View, status: CommonStatus) {
    when (status) {
        CommonStatus.LOADING -> view.visible()
        else -> view.invisible()
    }
}

@BindingAdapter("bgColor")
fun bindBgColor(view: View, hex: String) {
    view.setBackgroundColor(Color.parseColor(hex))
}



