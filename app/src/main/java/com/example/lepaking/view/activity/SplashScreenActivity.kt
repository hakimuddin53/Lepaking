package com.example.lepaking.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goLogin()

//        if (PermissionsHelper.checkPermissionsApp(this)) {
//            goLogin()
//        }
    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        if (requestCode == PermissionsHelper.REQUEST_CODE_APP_PERMISSIONS) {
//            var permissionDenied = false
//
//            for (result in grantResults) {
//                if (result == PackageManager.PERMISSION_DENIED) {
//                    permissionDenied = true
//                }
//            }
//
//            if (permissionDenied) {
//                Toast.makeText(this, getString(R.string.warning_permission_required), Toast.LENGTH_SHORT).show()
//            }else{
//                goLogin()
//            }
//        } else
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }

    private fun goLogin(){
        val intent = Intent(this,  MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}