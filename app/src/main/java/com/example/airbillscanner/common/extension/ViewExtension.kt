package com.example.airbillscanner.common.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import kotlinx.coroutines.*

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

/* usage example: val view = viewGroup[0]*/
operator fun ViewGroup.get(pos: Int): View = getChildAt(pos)

//Extension propery that return list of views
val ViewGroup.views: List<View> get() = (0 until childCount).map { getChildAt(it) }

fun View.hideKeyboard(): Boolean
{
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return imm.hideSoftInputFromWindow(this.windowToken, 0)
}

@ExperimentalCoroutinesApi
fun View.onSingleClick(function: () -> Unit) {
    GlobalScope.launch(Dispatchers.Main, CoroutineStart.UNDISPATCHED) {
        this@onSingleClick.isClickable = false
        function()
        delay(600)
        this@onSingleClick.isClickable = true
    }
}