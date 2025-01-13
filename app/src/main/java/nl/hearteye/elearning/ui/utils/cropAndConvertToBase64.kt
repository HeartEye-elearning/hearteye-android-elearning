package nl.hearteye.elearning.ui.utils

import android.content.Context
import android.net.Uri

fun cropAndConvertToBase64(uri: Uri, context: Context): String? {
    val bitmap = getBitmapFromUri(uri, context)
    bitmap?.let {
        val croppedBitmap = cropBitmapToSquare(it)
        return convertBitmapToBase64(croppedBitmap)
    }
    return null
}
