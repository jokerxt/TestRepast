package ru.jxt.testrepast.utils

import android.view.View

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide(gone: Boolean = true) {
    this.visibility = if(gone) View.GONE else View.INVISIBLE
}