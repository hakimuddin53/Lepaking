package com.example.lepaking.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.lepaking.R
import com.example.lepaking.common.eventbus.DropdownListChangeBus
import com.example.lepaking.model.ui.DropdownList
import com.google.android.material.textfield.TextInputLayout

/**
 *Created by jhcheng on 8/7/2019.
 */
class DropDownMenuView(context: Context, attrs: AttributeSet) : TextInputLayout(context, attrs) {

    private var dropDownMenu : AutoCompleteTextView
    private var list : List<DropdownList>? = null
    private val root = LayoutInflater.from(context).inflate(R.layout.view_dropdown_menu, this, false) as TextInputLayout

    init {
        dropDownMenu = root.findViewById(R.id.dropdown) as AutoCompleteTextView

        dropDownMenu.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            DropdownListChangeBus.send(list!![position])
        }

        // get custom attributes set in xml
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomEditText, 0,0).apply {
            try {
                val isRequired = getBoolean(R.styleable.CustomEditText_isRequired, false)

                if (isRequired) {
                    root.hint = "${getString(R.styleable.CustomEditText_label)}*"
                } else {
                    root.hint = getString(R.styleable.CustomEditText_label)
                }
            } finally {
                recycle()
            }
        }

        addView(root, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
    }

    fun setDataDropdown(list: List<DropdownList>) {
        dropDownMenu.setText("", false)
        this.list = list
        val items = list.map { it.message }
        val adapter = ArrayAdapter(context, R.layout.item_dropdown_menu_popup, R.id.text_value, items)
        dropDownMenu.setAdapter(adapter)
    }

    fun setSelectedItem(id: String) {
        list?.let { list ->
            if (list.isNotEmpty()) {
                val item = list.find { it.id == id }
                val index = list.indexOf(item)
                dropDownMenu.setText(dropDownMenu.adapter.getItem(index).toString(), false)
                dropDownMenu.dismissDropDown()
            }
        }
    }

    fun clearSelection() {
        dropDownMenu.setText("", false)
    }

    fun getDataSize() = list?.size

    fun setError(error: String?) {
        root.error = error
    }
}