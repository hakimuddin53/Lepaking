package com.example.airbillscanner.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.airbillscanner.R
import com.example.airbillscanner.view.fragment.AirBillFragment

class AirBillActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_airbill)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.content, AirBillFragment()).commit()
        }
    }
}