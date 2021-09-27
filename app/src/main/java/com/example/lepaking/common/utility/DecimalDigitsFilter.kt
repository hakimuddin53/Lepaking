package com.example.lepaking.common.utility

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

/**
 *Created by jhcheng on 8/1/2019.
 */
class DecimalDigitsFilter(totalDigits: Int, digitsAfterDot: Int): InputFilter {

    private val pattern = Pattern.compile("[0-9]{0," + (totalDigits - 1) + "}+((\\.[0-9]{0," + (digitsAfterDot - 1) + "})?)||(\\.)?")

    override fun filter(source: CharSequence?, start: Int, end: Int, destination: Spanned?, destinationStart: Int, destinationEnd: Int): CharSequence? {
        val matcher = pattern.matcher(destination)
        if (!matcher.matches()) {
            return ""
        }
        return null
    }
}