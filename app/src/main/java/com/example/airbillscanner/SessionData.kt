package com.example.airbillscanner

import android.content.SharedPreferences
import javax.inject.Inject

class SessionData @Inject constructor(private val preferences: SharedPreferences) {

    var username: String
        get() = preferences.getString(Constants.PREF_KEY_USERNAME, "") ?: ""
        set(value) = preferences.edit().putString(Constants.PREF_KEY_USERNAME, value).apply()
}