package com.example.airbillscanner.common.barcodescanning

import android.util.Log
import com.example.airbillscanner.common.eventbus.BarcodeDetectedBus
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import java.io.IOException

/** Barcode Detector Demo.  */
class BarcodeScanningProcessor: VisionProcessorBase<List<FirebaseVisionBarcode>>() {

    private val detector: FirebaseVisionBarcodeDetector = FirebaseVision.getInstance().visionBarcodeDetector

    init {
        // Note that if you know which format of barcode your app is dealing with, detection will be
        // faster to specify the supported barcode formats one by one, e.g.
        /*val options = FirebaseVisionBarcodeDetectorOptions.Builder()
        .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
        .build()*/
    }

    override fun stop() {
        try {
            detector.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close Barcode Detector: $e")
        }
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<List<FirebaseVisionBarcode>> {
        return detector.detectInImage(image)
    }

    override fun onSuccess(
        barcodes: List<FirebaseVisionBarcode>,
        frameMetadata: FrameMetadata,
        graphicOverlay: GraphicOverlay
    ) {
        graphicOverlay.clear()
        for (i in barcodes.indices) {
            val barcode = barcodes[i]
            val barcodeGraphic = BarcodeGraphic(graphicOverlay, barcode)
            graphicOverlay.add(barcodeGraphic)
            val barcodeValue = barcode.rawValue
            BarcodeDetectedBus.send(barcodeValue ?: "");
        }
    }

    override fun onFailure(e: Exception) {
        Log.e(TAG, "Barcode detection failed $e")
    }

    companion object {

        private val TAG = "BarcodeScanProc"
    }
}