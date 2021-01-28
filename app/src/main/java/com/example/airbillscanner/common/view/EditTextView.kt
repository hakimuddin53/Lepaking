package com.example.airbillscanner.common.view

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.airbillscanner.R
import com.example.airbillscanner.common.extension.hideKeyboard
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


/**
 *Created by jhcheng on 8/8/2019.
 */
class EditTextView(context: Context, attrs: AttributeSet) : TextInputLayout(context, attrs) {

    private val root = LayoutInflater.from(context).inflate(R.layout.view_edit_text, this, false) as TextInputLayout

    init {
        // get custom attributes set in xml
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomEditText, 0,0).apply {
            try {
                val isRequired = getBoolean(R.styleable.CustomEditText_isRequired, false)

                if (isRequired) {
                    root.hint = "${getString(R.styleable.CustomEditText_label)}*"
                } else {
                    root.hint = getString(R.styleable.CustomEditText_label)
                }

                root.endIconMode = getInt(R.styleable.CustomEditText_iconMode, 2)
                if (root.endIconMode == -1) {
                    root.endIconDrawable = getDrawable(R.styleable.CustomEditText_iconDrawable)
                }

                getTextInputEditText().setOnFocusChangeListener { view, isFocus ->
                    if (isFocus && getTextInputEditText().inputType == InputType.TYPE_NULL) {
                        view.hideKeyboard()
                    }
                }

            } finally {
                recycle()
            }
        }
        addView(root, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
    }

    fun setError(error: String?) {
        root.error = error
    }

    fun getTextInputLayout() = root

    fun getTextInputEditText() = root.findViewById(R.id.edit_text) as TextInputEditText
}