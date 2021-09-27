package com.example.lepaking.common.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lepaking.viewmodel.BaseViewModelFactory

// Extend a Fragment (or Activity) and add a getViewModel method which wraps the ViewModelProvider
internal fun <T : ViewModel> Fragment.getViewModel(modelClass: Class<T>, viewModelFactory: ViewModelProvider.Factory? = null): T {
    return viewModelFactory?.let { ViewModelProvider(this, it).get(modelClass) } ?: ViewModelProvider(this).get(modelClass)
}

internal fun <T : ViewModel> FragmentActivity.getViewModel(modelClass: Class<T>, viewModelFactory: ViewModelProvider.Factory? = null): T {
    return viewModelFactory?.let { ViewModelProvider(this, it).get(modelClass) } ?: ViewModelProvider(this).get(modelClass)
}

inline fun <reified T: ViewModel> Fragment.getViewModelNew(noinline creator: (() -> T)? = null) : T {
    return if (creator == null) {
        ViewModelProvider(this).get(T::class.java)
    } else {
        ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    }
}

inline fun <reified T: ViewModel> FragmentActivity.getViewModelNew(noinline creator: (() -> T)? = null) : T {
    return if (creator == null) {
        ViewModelProvider(this).get(T::class.java)
    } else {
        ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    }
}