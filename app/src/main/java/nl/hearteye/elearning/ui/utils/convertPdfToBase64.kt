package nl.hearteye.elearning.ui.utils

import android.util.Base64
import java.io.File
import java.io.FileInputStream

fun convertPdfToBase64(pdfFile: File): String? {
    return try {

        val fileInputStream = FileInputStream(pdfFile)
        val fileBytes = fileInputStream.readBytes()
        fileInputStream.close()


        val base64String = Base64.encodeToString(fileBytes, Base64.NO_WRAP)
        base64String
    } catch (e: Exception) {
        null
    }
}
