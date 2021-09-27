package com.example.lepaking.common.binding

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.lepaking.R
import com.example.lepaking.common.GlideApp
import com.example.lepaking.common.utility.DecimalDigitsFilter
import com.example.lepaking.common.view.EditTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.File


object BindingAdapterUtils {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {

/*        view.load(url){
            placeholder(R.drawable.ic_image_placeholder_120dp)
        }*/

        GlideApp.with(view)
            .load(url)
            .placeholder(R.drawable.ic_image_placeholder_120dp)
            .into(view)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setImage(view: ImageView, file: File) {

      /*  view.load(file){
            placeholder(R.drawable.ic_image_placeholder_120dp)
        }*/

        GlideApp.with(view)
            .load(file)
            .placeholder(R.drawable.ic_image_placeholder_120dp)
            .into(view)
    }

    /*@BindingAdapter("android:src")
    @JvmStatic
    fun setSrcVector(view: ImageView, drawable: Drawable) {
        GlideApp.with(view)
            .load(drawable)
            .placeholder(R.drawable.ic_image_placeholder_120dp)
            .fitCenter()
            .into(view)
    }*/

    @BindingAdapter("android:src")
    @JvmStatic
    fun setSrcVector(view: ImageView, drawable: Int) {

   /*     view.load(drawable){
            placeholder(R.drawable.ic_image_placeholder_120dp)
        }*/

        GlideApp.with(view)
            .load(drawable)
            .placeholder(R.drawable.ic_image_placeholder_120dp)
            .fitCenter()
            .into(view)
    }

    @BindingAdapter("app:goneUnless")
    @JvmStatic
    fun goneUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("android:errorText")
    @JvmStatic
    fun setErrorMessage(view: TextInputLayout, errorMessage: String) {
        if(errorMessage.isBlank()) {
            view.error = null
        }else{
            view.error = errorMessage
        }
    }

    @BindingAdapter("app:cursorPosition")
    @JvmStatic
    fun setCursorPosition(editText: EditText, value: String?) {
        value ?: return
        editText.setSelection(value.length)
    }

    @BindingAdapter("app:text")
    @JvmStatic
    fun setText(view: EditTextView, value: String) {
        val editText = view.findViewById(R.id.edit_text) as TextInputEditText
        if (editText.text.toString() != value) {
            editText.setText(value)
        }
    }

    @InverseBindingAdapter(attribute = "app:text")
    @JvmStatic
    fun getText(view: EditTextView) : String {
        val editText = view.findViewById(R.id.edit_text) as TextInputEditText
        return editText.text.toString()
    }

    @BindingAdapter("app:textAttrChanged")
    @JvmStatic
    fun setListeners(view: EditTextView, listener: InverseBindingListener) {
        val editText = view.findViewById(R.id.edit_text) as TextInputEditText
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                listener.onChange()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    @BindingAdapter("app:inputType")
    @JvmStatic
    fun setInputType(view: EditTextView, value: Int) {

        if (value == InputType.TYPE_NUMBER_FLAG_DECIMAL) {
            view.getTextInputEditText().inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL
            view.getTextInputEditText().filters = arrayOf(DecimalDigitsFilter(10, 2))
        } else {
            view.getTextInputEditText().inputType = value
        }
    }
}