package com.example.airbillscanner.common.provider

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.telephony.TelephonyManager
import com.resmal.smartsales.common.utility.SystemUtility


class SystemInformationProvider(val context: Context)
{
    @SuppressLint("HardwareIds")
    fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getSystemLanguage(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0].language;
        } else {
            context.resources.configuration.locale.language;
        }
    }

    fun getDeviceSimProvider(): String {
        val telephonyManager = context.applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.networkOperatorName
    }

    fun getNetworkType(): String {
        var cellularType = ""

        if (SystemUtility.isConnectedInternet(context) && SystemUtility.isConnectedMobile(context)) {

            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val networkType = telephonyManager.networkType
            when (networkType) {
                TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> cellularType = "2G"
                TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> cellularType = "3G"
                TelephonyManager.NETWORK_TYPE_LTE -> cellularType = "4G"
                else -> return "Unknown"
            }
        } else if (SystemUtility.isConnectedInternet(context) && SystemUtility.isConnectedWifi(context)) {
            return "WiFi"
        }

        return "Cellular $cellularType"
    }

//    fun getNetworkAddress(): String {
//        var networkAddress = ""
//        if (SystemUtility.isConnectedInternet(context)) {
//            if (SystemUtility.isConnectedWifi(context)) {
//                val wifiManager =
//                    context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//                val ipAddress = wifiManager.connectionInfo.ipAddress
//                networkAddress = Formatter.formatIpAddress(ipAddress)
//            } else if (SystemUtility.isConnectedMobile(context)) {
//                try {
//                    val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
//                    for (intf in interfaces) {
//                        val addresses = Collections.list(intf.inetAddresses)
//                        for (address in addresses) {
//                            if (!address.isLoopbackAddress) {
//                                return address.hostAddress
//                            }
//                        }
//                    }
//                } catch (ex: Exception) {
//                    Timber.e(ex, ex.message)
//                }
//
//            }
//        }
//        return networkAddress
//    }

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

//    fun getDeviceType(): String {
//        return if (context.resources.getBoolean(R.bool.isTablet)) {
//            "Tablet"
//        }
//        else {
//            "Phone"
//        }
//    }

//    fun getWifiMACAddress(): String {
//        val manager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//        val info = manager.connectionInfo
//        return info.macAddress
//    }

//    fun getBluetoothMACAddress(): String {
//        val manager = context.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//        val adapter = manager.adapter
//        if(adapter != null)
//        {
//            return adapter.address
//        }
//        return "N/A"
//    }

    fun getMemoryCapacity(): String {
        val info = ActivityManager.MemoryInfo()
        return formatStorageSize(info.totalMem)
    }

    fun getInternalStorageCapacity(): String {
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

            if (size >= 1024) {
                suffix = "MB"
                size /= 1024

                if (size > 1024)
                {
                    suffix = "GB"
                    size /= 1024
                }
            }
        }

        val result = StringBuilder(java.lang.Long.toString(size))

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

    fun getApplicationVersion() : String{
        val manager = context.packageManager
        val info = manager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)

        return "v" + info.versionName
    }
}