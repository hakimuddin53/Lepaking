package com.example.airbillscanner.view.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.airbillscanner.R
import com.example.airbillscanner.common.barcodescanning.BarcodeScanningProcessor
import com.example.airbillscanner.common.barcodescanning.CameraSource
import com.example.airbillscanner.common.barcodescanning.CameraSourcePreview
import com.example.airbillscanner.common.barcodescanning.GraphicOverlay
import com.example.airbillscanner.common.eventbus.BarcodeDetectedBus
import com.example.airbillscanner.common.extension.addOnPropertyChanged
import com.example.airbillscanner.common.extension.getViewModel
import com.example.airbillscanner.common.extension.toast
import com.example.airbillscanner.databinding.ActivityBarcodeScanningBinding
import com.example.airbillscanner.viewmodel.BarcodeScanningViewModel
import com.google.firebase.ml.common.FirebaseMLException
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_barcode_scanning.*
import java.io.IOException
import java.util.ArrayList

class BarcodeScanningActivity: AppCompatActivity()
{
    companion object {
        const val BARCODE_ACTIVITY_CODE = 999
        const val BARCODE_KEY = "barcode_key"
    }

    private val PERMISSION_REQUESTS = 1
    private var cameraSource: CameraSource? = null
    private var preview: CameraSourcePreview? = null
    private var graphicOverlay: GraphicOverlay? = null
    private lateinit var viewModel: BarcodeScanningViewModel
    private lateinit var binding: ActivityBarcodeScanningBinding
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_barcode_scanning)

        viewModel = getViewModel(BarcodeScanningViewModel::class.java)
        binding.viewModel = viewModel

        initializeBarcodeScanner()
        createCameraSource()
        subscribeToModel()

        btn_close.setOnClickListener {
            finish()
        }
    }

    private fun subscribeToModel()
    {
        disposables.add(BarcodeDetectedBus.toObservable().subscribe {
            viewModel.barcode.set(it)

        })

        viewModel.isCameraFacingFront.addOnPropertyChanged {
            if(it.get())
            {
                cameraSource?.setFacing(CameraSource.CAMERA_FACING_FRONT)
            }else{
                cameraSource?.setFacing(CameraSource.CAMERA_FACING_BACK)
            }
            preview?.stop()
            startCameraSource()
        }

        viewModel.confirmClicked.addOnPropertyChanged {

            val barcode = viewModel.barcode.get() ?: ""
            val validBarcode = validateBarcode(barcode)

            if(validBarcode)
            {
                returnBarcodeResult(barcode)
            }else{
                showErrorToast()
            }
        }
    }

    private fun validateBarcode(barcode: String): Boolean
    {
        if(barcode.isNotEmpty())
        {
            return true
        }

        return false
    }

    private fun returnBarcodeResult(barcode: String)
    {
        val intent = Intent()
        intent.putExtra(BARCODE_KEY, barcode)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun showErrorToast()
    {
        toast(getString(R.string.error_barcode_invalid))
    }

    fun initializeBarcodeScanner()
    {
        preview = firePreview as CameraSourcePreview
        graphicOverlay = fireFaceOverlay as GraphicOverlay
    }

    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    /** Stops the camera.  */
    override  fun onPause() {
        super.onPause()
        preview?.stop()
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    private fun createCameraSource() {
        // If there's no existing cameraSource, create one.
        if (cameraSource == null) {
            cameraSource = CameraSource(this, graphicOverlay!!)
        }

        try {
            cameraSource?.setMachineLearningFrameProcessor(BarcodeScanningProcessor())
        } catch (e: FirebaseMLException) {
        }

    }

    /**
     * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private fun startCameraSource() {
        if (cameraSource != null) {
            try {
                preview?.start(cameraSource!!, graphicOverlay!!)
            } catch (e: IOException) {
                cameraSource?.release()
                cameraSource = null
            }
        }
    }

    private fun getRequiredPermissions(): Array<String?> {
        try {
            val info = this.packageManager
                .getPackageInfo(this.packageName, PackageManager.GET_PERMISSIONS)
            val ps = info.requestedPermissions
            return if (ps != null && ps.size > 0) {
                ps
            } else {
                arrayOfNulls(0)
            }
        } catch (e: Exception) {
            return arrayOfNulls(0)
        }

    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission!!)) {
                return false
            }
        }
        return true
    }

    private fun getRuntimePermissions() {
        val allNeededPermissions = ArrayList<String>()
        for (permission in getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission!!)) {
                allNeededPermissions.add(permission)
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                this, allNeededPermissions.toTypedArray(), PERMISSION_REQUESTS)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (allPermissionsGranted()) {
            createCameraSource()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun isPermissionGranted(context: Context, permission: String): Boolean {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }
}