package com.san.leng.core.extensions

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.san.leng.core.utils.getWordIndexBySelectionStart


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.showToast(message: String, lengthLong: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, message, lengthLong).show()
}

fun View.setMargins(
    leftMarginDp: Int? = null,
    topMarginDp: Int? = null,
    rightMarginDp: Int? = null,
    bottomMarginDp: Int? = null
) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        leftMarginDp?.run { params.leftMargin = this.dpToPx(context) }
        topMarginDp?.run { params.topMargin = this.dpToPx(context) }
        rightMarginDp?.run { params.rightMargin = this.dpToPx(context) }
        bottomMarginDp?.run { params.bottomMargin = this.dpToPx(context) }
        requestLayout()
    }
}

fun Int.dpToPx(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
}


fun EditText.getClickedWord(): String {
    val startSelection = this.selectionStart

    var length = 0
    var selectedWord = ""
    val wordsArray = this.text.toString().split(" ".toRegex()).toTypedArray()

    for (currentWord in wordsArray) {
        length += currentWord.length + 1
        if (length > startSelection) {
            selectedWord = currentWord
            break
        }
    }
    return selectedWord
}

fun EditText.setHighlight(textToHighlight: String?) {

    textToHighlight?.let {
        val text = this.text.toString()
        val match = textToHighlight.toRegex().find(text)
        val index: Int =
            getWordIndexBySelectionStart(match, this.selectionStart) ?: return

        this.setSelection(index, index + textToHighlight.length)
    }
}
