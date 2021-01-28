package com.example.airbillscanner.common.barcodescanning

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode

/** Graphic instance for rendering Barcode position and message information in an overlay view.  */
class BarcodeGraphic internal constructor(overlay: GraphicOverlay, private val barcode: FirebaseVisionBarcode?) : GraphicOverlay.Graphic(overlay) {

    private val rectPaint: Paint = Paint()
    private val barcodePaint: Paint

    init {

        rectPaint.color = TEXT_COLOR
        rectPaint.style = Paint.Style.STROKE
        rectPaint.strokeWidth = STROKE_WIDTH

        barcodePaint = Paint()
        barcodePaint.color = TEXT_COLOR
        barcodePaint.textSize = TEXT_SIZE
        // Redraw the overlay, as this graphic has been added.
        postInvalidate()
    }

    /**
     * Draws the barcode block annotations for position, size, and raw value on the supplied canvas.
     */
    override fun draw(canvas: Canvas) {
        if (barcode == null) {
            throw IllegalStateException("Attempting to draw a null barcode.")
        }

        // Draws the bounding box around the BarcodeBlock.
        val rect = RectF(barcode.boundingBox)
        rect.left = translateX(rect.left)
        rect.top = translateY(rect.top)
        rect.right = translateX(rect.right)
        rect.bottom = translateY(rect.bottom)
        canvas.drawRect(rect, rectPaint)

        // Renders the barcode at the bottom of the box.
        canvas.drawText(barcode.rawValue!!, rect.left, rect.bottom, barcodePaint)
    }

    companion object {

        private const val TEXT_COLOR = Color.WHITE
        private const val TEXT_SIZE = 54.0f
        private const val STROKE_WIDTH = 4.0f
    }
}
