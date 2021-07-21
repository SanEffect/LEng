package com.san.leng.core.extensions

import android.view.View

fun View.rotateBy(degrees: Float, duration: Long) {
    this.animate().rotationBy(degrees).setDuration(duration).start()
}
