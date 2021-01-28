package com.example.airbillscanner.common.extension

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal fun <T : ViewModel> AppCompatActivity.getViewModel(modelClass: Class<T>, viewModelFactory: ViewModelProvider.Factory? = null): T {
    return viewModelFactory?.let { ViewModelProvider(this, it).get(modelClass) } ?: ViewModelProvider(this).get(modelClass)
}

inline fun <reified T : View> AppCompatActivity.find(id: Int): T = findViewById(id) as T

internal fun AppCompatActivity.toast(message: String, length: Int = Toast.LENGTH_SHORT)
{
    Toast.makeText(this, message, length).show()
}