package com.example.airbillscanner.common.helper

import android.app.Activity
import android.content.Context
import android.content.Intent

class IntentHelper{

    companion object {

        //TODO: Add animation and effect in future
        fun startActivity(context: Context, intent: Intent){
            context.startActivity(intent)

        }

        fun startActivityForResult(activity: Activity,  intent: Intent , resultCode : Int){
            activity.startActivityForResult(intent , resultCode)
        }
    }
}
