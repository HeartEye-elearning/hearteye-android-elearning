package nl.hearteye.elearning.ui.utils

import android.graphics.Bitmap

fun cropBitmapToSquare(bitmap: Bitmap): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val size = Math.min(width, height)

    val xOffset = (width - size) / 2
    val yOffset = (height - size) / 2

    return Bitmap.createBitmap(bitmap, xOffset, yOffset, size, size)
}
