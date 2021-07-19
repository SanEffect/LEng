package com.san.leng.core.extensions

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.san.leng.R
import com.san.leng.core.Event
import com.san.leng.core.utils.getWordIndexBySelectionStart

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun View.setVisibility(state: Boolean) {
    this.visibility = if(state) View.VISIBLE else View.GONE
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.showToast(message: String, lengthLong: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, message, lengthLong).show()
}

fun View.showToast(message: Int?, lengthType: Int = Toast.LENGTH_SHORT) {
    message?.let {
        Toast.makeText(this.context, this.context.getString(message), lengthType).show()
    }
}

fun View.showToast(message: String?, lengthType: Int = Toast.LENGTH_SHORT) {
    message?.let {
        Toast.makeText(this.context, message, lengthType).show()
    }
}

fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Int>>,
    timeLength: Int = Snackbar.LENGTH_SHORT
) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            showSnackbar(context.getString(it), timeLength)
        }
    })
}

fun View.showSnackbar(snackbarText: String, timeLength: Int = Snackbar.LENGTH_SHORT) {
//    this.rootView.findViewById<FloatingActionButton>(R.id.add_record_fab).apply {
//        Snackbar.make(this, snackbarText, timeLength).setAnchorView(this).show()
//    }
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
