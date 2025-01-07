package nl.hearteye.elearning.ui.utils

import android.util.Base64
import android.util.Log
import java.io.File
import java.io.FileInputStream

fun convertPdfToBase64(pdfFile: File): String? {
    return try {
        Log.d("convertPdfToBase64", "Converting file to Base64. File: ${pdfFile.name}, Size: ${pdfFile.length()} bytes")

        val fileInputStream = FileInputStream(pdfFile)
        val fileBytes = fileInputStream.readBytes()
        fileInputStream.close()

        Log.d("convertPdfToBase64", "File read successfully. Bytes length: ${fileBytes.size}")

        val base64String = Base64.encodeToString(fileBytes, Base64.NO_WRAP)
        Log.d("convertPdfToBase64", "Base64 string generated. Length: ${base64String.length}")
        base64String
    } catch (e: Exception) {
        Log.e("convertPdfToBase64", "Error converting file to Base64: ${e.message}", e)
        null
    }
}
