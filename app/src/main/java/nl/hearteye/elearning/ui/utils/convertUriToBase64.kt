package nl.hearteye.elearning.ui.utils

import android.content.ContentResolver
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream

fun convertUriToBase64(uri: Uri, contentResolver: ContentResolver): String {
    // Use ContentResolver to get an InputStream for the URI
    val inputStream: InputStream = contentResolver.openInputStream(uri) ?: return ""
    val byteArrayOutputStream = ByteArrayOutputStream()

    val buffer = ByteArray(1024)
    var bytesRead: Int

    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
        byteArrayOutputStream.write(buffer, 0, bytesRead)
    }

    inputStream.close()

    // Convert byte array to Base64 string
    val fileBytes = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(fileBytes, Base64.DEFAULT)
}