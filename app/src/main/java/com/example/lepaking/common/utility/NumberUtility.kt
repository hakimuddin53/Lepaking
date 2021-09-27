package com.example.lepaking.common.utility

import androidx.databinding.ObservableField
import com.example.lepaking.common.extension.getNonNull

import java.util.*

class NumberUtility {

    companion object {
        fun getUUID(): String = UUID.randomUUID().toString()

        fun convertObservableStringToDouble(value: ObservableField<String>): Double {

            return if (value.getNonNull().toString().isEmpty()) 0.0 else value.getNonNull().toString().toDouble()
        }

        fun convertStringToDouble(value : String) : Double {
            try {
                if(value.isEmpty())
                   return  0.00
                else
                    return  value.toDouble()

            } catch (e: Exception) {
                return 0.00
            }
        }
    }
}