package com.example.lepaking.common.provider

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import java.io.File

/**
 */
class ResourceProvider(private val context: Context) {

    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

    fun getString(@StringRes stringResId: Int, value: String): String {
        return context.getString(stringResId, value)
    }

    fun getDrawable(@DrawableRes drawableResId: Int): Drawable
    {
        return ContextCompat.getDrawable(context, drawableResId)!!
    }

    fun getColor(@ColorRes colorResId: Int): Int{
        return ContextCompat.getColor(context, colorResId)
    }

    fun getExternalFilesDirectory(type: String?): File? {
        return context.getExternalFilesDir(type)
    }

    fun getContext() : Context {
        return context
    }
}