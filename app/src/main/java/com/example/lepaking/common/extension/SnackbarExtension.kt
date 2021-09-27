package com.example.lepaking.common.extension

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.example.lepaking.R
import com.google.android.material.snackbar.Snackbar


inline fun View.snack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    snack(resources.getString(messageRes), length, f)
}

fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, @ColorRes backgroundColorRes: Int? = null, @ColorRes textColorRes: Int? = null) {
    val snack = Snackbar.make(this, message, length)
    backgroundColorRes?.let {
        snack.view.setBackgroundColor(it)
    }
    textColorRes?.let {
        snack.view.findViewById<TextView>(R.id.snackbar_text).setTextColor(it)
    }
    snack.show()
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(@IntegerRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}