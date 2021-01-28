package com.resmal.smartsales.common.utility

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.hardware.fingerprint.FingerprintManager
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.*
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.format.Formatter
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import timber.log.Timber
import java.net.NetworkInterface
import java.util.*

/**
 * Created by iChris on 9/12/17.
 */
class SystemUtility {

    companion object {
        fun shutdownApplication(activity: Activity) {
            activity.finishAffinity()
        }

        fun hideSoftKeyboard(view: View, context: Context)
        {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun hideKeyboard(activity: Activity){
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun hideSoftKeyboard(activity: Activity?) {
            if (activity?.window != null) {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
            }
        }

        fun hideSoftKeyboardForced(activity: Activity) {
            if (activity.window != null) {
                activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            }
        }

        fun showSoftKeyboard(activity: Activity) {
            if (activity.window != null) {
                activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
        }

        fun showSoftKeyboardForced(activity: Activity) {
            if (activity.window != null) {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            }
        }

        fun getSDKVersion(): Int {
            return Build.VERSION.SDK_INT
        }

        fun getAndroidOSVersion(): String {
            return Build.VERSION.CODENAME + " - " + Build.VERSION.INCREMENTAL + " (" + Build.VERSION.RELEASE + ")"
        }

        fun getProcessorInfo(): String {
            return Build.BOARD
        }

        fun getDeviceManufacturer(): String {
            return Build.MANUFACTURER
        }

        fun getDeviceBrand(): String {
            return Build.BRAND
        }

        fun getDeviceName(): String {
            return Build.MODEL
        }

        @SuppressLint("HardwareIds")
        fun getWLANMACAddress(context: Context): String {
            val manager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val info = manager.connectionInfo
            return info.macAddress
        }

        @SuppressLint("HardwareIds")
        fun getBluetoothMACAddress(context: Context): String {
            val manager = context.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            val adapter = manager.adapter
            return adapter.address
        }

        fun getTotalRAMSize(): String {
            val info = ActivityManager.MemoryInfo()
            return formatStorageSize(info.totalMem)
        }

        fun getTotalInternalStorageSize(context: Context): String {
            val stat = StatFs(Environment.getDataDirectory().path)
            val blockSize = stat.blockSizeLong
            val totalBlocks = stat.blockCountLong

            return formatStorageSize(totalBlocks * blockSize)
        }

        private fun formatStorageSize(storageSize: Long): String {
            var size = storageSize
            var suffix = ""
            if (size >= 1024) {
                suffix = "KB"
                size /= 1024
                if (size > 1024) {
                    suffix = "MB"
                    size /= 1024
                }
            }

            val result = StringBuilder(size.toString())

            var commaOffset = result.length - 3
            while (commaOffset > 0) {
                result.insert(commaOffset, ',')
                commaOffset -= 3
            }

            if (suffix.isNotEmpty()) {
                result.append(suffix)
            }
            return result.toString()
        }

        //This method will return an Intent that helps navigate to this application settings
        fun getNavigateToAppSettingsIntent(packageName: String): Intent {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)

            return intent
        }

        /*fun playNotificationSound(context: Context) {
            val ringtoneUri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.barcode_scanned)
            val mp = MediaPlayer.create(context, ringtoneUri)
            mp.start()
        }*/

        fun vibrate(context: Context, duration: Long, amplitude: Int = 10) {
            val vibrator = context.applicationContext.getSystemService(VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude));
            } else {
                vibrator.vibrate(duration);
            }
        }

        fun isGooglePlayServiceAvailable(activity: Activity, PLAY_SERVICE_REQUEST_CODE: Int): Boolean {
            val googleApiAvailability = GoogleApiAvailability.getInstance()
            val code = googleApiAvailability.isGooglePlayServicesAvailable(activity)
            if (code != ConnectionResult.SUCCESS) {
                if (googleApiAvailability.isUserResolvableError(code)) {
                    googleApiAvailability.getErrorDialog(activity, code, PLAY_SERVICE_REQUEST_CODE).show()
                } else {
                    Toast.makeText(activity, "This device does not support google play services.", Toast.LENGTH_SHORT).show()
                    activity.finish()
                }
                return false
            }
            return true
        }

        fun isConnectedInternet(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork?.isConnected ?: false
        }

        fun getDeviceIMEI(context: Context): String {
            return (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
        }

        @SuppressLint("HardwareIds")
        fun getDeviceId(context: Context): String {
            return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }

        /*fun getDeviceType(context: Context): String {
            if (context.resources.getBoolean(R.bool.isTablet))
                return context.getString(R.string.device_type_tablet)
            else
                return context.getString(R.string.device_type_phone)
        }*/

        fun getDevicePhoneNumber(context: Context): String? {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var phoneNumber: String? = telephonyManager.line1Number
            if (phoneNumber == null)
                phoneNumber = getDeviceSimNumber(context)
            return phoneNumber
        }

        fun getDeviceSimNumber(context: Context): String? {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return telephonyManager.simSerialNumber
        }

        fun getDeviceSimProvider(context: Context): String {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return telephonyManager.networkOperatorName
        }

        fun getDeviceSimCountry(context: Context): String {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return telephonyManager.simCountryIso
        }

        fun getDeviceLanguage(): String {
            return Locale.getDefault().language
        }

        fun getDeviceCountry(context: Context): String {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return context.resources.configuration.locales.get(0).toString()
            } else {
                return context.resources.configuration.locale.toString()
            }
        }

        fun isConnectedWifi(context: Context): Boolean {
            val info = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI
        }

        fun isConnectedMobile(context: Context): Boolean {
            val info = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE
        }

        fun deviceSupportFingerprint(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val fingerprintManager = context.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT)) {
                    if (!fingerprintManager.isHardwareDetected) {
                        return false
                    } else return fingerprintManager.hasEnrolledFingerprints()
                }
            }
            return false
        }

        fun getNetworkType(context: Context): String {
            var cellularType = ""

            if (isConnectedInternet(context) && isConnectedMobile(context)) {

                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                cellularType = when (telephonyManager.networkType) {
                    TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> "2G"
                    TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> "3G"
                    TelephonyManager.NETWORK_TYPE_LTE -> "4G"
                    else -> return "Unknown"
                }
            } else if (isConnectedInternet(context) && isConnectedWifi(context)) {
                return "WiFi"
            }

            return "Cellular $cellularType"
        }

        fun getNetworkAddress(context: Context): String {
            var networkAddress = ""
            if (isConnectedInternet(context)) {
                if (isConnectedWifi(context)) {
                    val wifiManager =
                            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                    val ipAddress = wifiManager.connectionInfo.ipAddress
                    networkAddress = Formatter.formatIpAddress(ipAddress)
                } else if (isConnectedMobile(context)) {
                    try {
                        val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
                        for (intf in interfaces) {
                            val addresses = Collections.list(intf.inetAddresses)
                            for (address in addresses) {
                                if (!address.isLoopbackAddress) {
                                    return address.hostAddress
                                }
                            }
                        }
                    } catch (ex: Exception) {
                        Timber.e(ex)
                    }

                }
            }
            return networkAddress
        }

        fun getScreenWidth(context: Context): Int {
            val screenWidth: Int
            val wm = context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay

            val point = Point()
            try {
                display.getSize(point)
            } catch (ignore: java.lang.NoSuchMethodError) { // Older device
                point.x = display.width
                point.y = display.height
            }

            screenWidth = point.x
            return screenWidth
        }

        fun getScreenHeight(context: Context): Int {
            val screenHeight: Int
            val wm = context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay

            val point = Point()
            try {
                display.getSize(point)
            } catch (ignore: java.lang.NoSuchMethodError) { // Older device
                point.x = display.width
                point.y = display.height
            }

            screenHeight = point.y
            return screenHeight
        }

        fun createPhoneDialIntent(phoneNumber: String): Intent =
                Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))

        fun isMobileTimeSetToAutomatic(context: Context) : Boolean {
            return Settings.Global.getInt(context.contentResolver, Settings.Global.AUTO_TIME, 0) == 1 &&
                Settings.Global.getInt(context.contentResolver, Settings.Global.AUTO_TIME_ZONE, 0) == 1
        }
    }
}