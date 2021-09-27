package com.example.lepaking.common.extension

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import io.reactivex.disposables.Disposables

fun <T: Any> ObservableField<T>.getNonNull(): T = get()!!

@Suppress("UNCHECKED_CAST")
fun <T: Observable> T.addOnPropertyChanged(callback: (T) -> Unit) =
        object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable?, i: Int) =
                    callback(observable as T)
        }.also { addOnPropertyChangedCallback(it) }.let {
            Disposables.fromAction {removeOnPropertyChangedCallback(it)}
        }

fun ObservableField<String>.toDouble() : Double  {
    return if(this.get().isNullOrBlank())
    {
        0.00
    }else{
        this.getNonNull().toString().toDouble()
    }
}

fun ObservableField<String>.toInt() : Int  {
    return if(this.get().isNullOrBlank())
    {
        0
    }else{
        this.getNonNull().toString().toInt()
    }
}
