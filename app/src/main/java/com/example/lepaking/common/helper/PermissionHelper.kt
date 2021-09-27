package com.example.lepaking.common.helper

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.provider.Settings


class PermissionsHelper {
    companion object {

        val REQUEST_CODE_CAMERA = 22
        val REQUEST_CODE_CALL_PHONE = 11
        val REQUEST_CODE_APP_PERMISSIONS = 33
        val REQUEST_CODE_WRITE_EXTERNAL = 44

        fun checkPermissionsCamera(activity: Activity): Boolean {
            val permissions = booleanArrayOf(true)
            val hasCameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            val hasReadFilePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED || hasReadFilePermission != PackageManager.PERMISSION_GRANTED) {

                permissions[0] = false
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_CODE_CAMERA)

            }
            return permissions[0]
        }

        fun checkPermissionsCallphone(activity: Activity): Boolean {
            val permissions = booleanArrayOf(true)
            val hasCallPhonePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
            if (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED) {

                permissions[0] = false
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CALL_PHONE),
                        REQUEST_CODE_CALL_PHONE)

            }
            return permissions[0]
        }



        fun checkPermissionsApp(activity: Activity): Boolean {
            val permissions = booleanArrayOf(true)

            val hasCameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            val hasStoragePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val hasStorageReadPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
            val hasInternetPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET)

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED ||
                hasStoragePermission != PackageManager.PERMISSION_GRANTED ||
                hasStorageReadPermission != PackageManager.PERMISSION_GRANTED ||
                hasInternetPermission != PackageManager.PERMISSION_GRANTED ) {

                permissions[0] = false
                ActivityCompat.requestPermissions(activity, arrayOf(
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET),
                    REQUEST_CODE_APP_PERMISSIONS)

//                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)
//                        && ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
//
////                    val dialogCustom = DialogCustom(activity)
////                    dialogCustom.setCancelable(false)
////                    dialogCustom.setTitle(activity.getString(R.string.txt_permissions_app))
////                    dialogCustom.setOKClicked(View.OnClickListener {
////                        dialogCustom.dismiss()
////
////                        goToSetting(activity)
////                    })
////                    dialogCustom.show()
//
//                } else {
//
//                }


            }

            return permissions[0]
        }

        /* fun openSettingPermissions(activity: Activity, permissions: permissionsEnum) {
             val Message: String
             val dialog = DialogConfirmCustom(activity)
             if (permissions == permissionsEnum.CAMERA) {
                 Message = activity.getString(R.string.txt_permissions_camera)
             } else {
                 Message = activity.getString(R.string.txt_permissions_call_phone)
             }
             dialog.setMessage(Message, "",
                     activity.getString(R.string.no),
                     activity.getString(R.string.yes),
                     View.OnClickListener { dialog.dismiss() }, View.OnClickListener {
                 dialog.dismiss()

                 val intent = Intent()
                 intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                 val uri = Uri.fromParts("package", activity.packageName, null)
                 intent.data = uri
                 activity.startActivity(intent)
             })
         }*/


        fun checkPermissionWriteExternal(activity: Activity): Boolean {
            val permissions = booleanArrayOf(true)
            val hasCallWriteExternalPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (hasCallWriteExternalPermission != PackageManager.PERMISSION_GRANTED) {
                permissions[0] = false
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_CODE_WRITE_EXTERNAL)

            }

            return permissions[0]
        }

        fun goToSetting(activity: Activity) {

            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivityForResult(intent, REQUEST_CODE_APP_PERMISSIONS)
        }

    }

    enum class permissionsEnum {
        CALL_PHONE, CAMERA
    }

}
