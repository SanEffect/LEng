package com.san.leng.core.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.showToast(message: String, lengthLong: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, message, lengthLong).show()
}