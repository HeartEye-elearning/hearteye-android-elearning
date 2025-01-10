package nl.hearteye.elearning.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL
import java.util.UUID

suspend fun renderPdfPage(context: Context, pdfUrl: String, pageIndex: Int = 0): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val uniqueFileName = UUID.randomUUID().toString() + ".pdf"
            val file = File(context.cacheDir, uniqueFileName)

            if (file.exists()) {
                file.delete()
            }

            val url = URL(pdfUrl)
            val connection = url.openConnection()
            connection.connect()
            file.outputStream().use { output ->
                connection.getInputStream().use { input ->
                    input.copyTo(output)
                }
            }

            val parcelFileDescriptor =
                ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(parcelFileDescriptor)

            val pdfPage = pdfRenderer.openPage(pageIndex)
            val bitmap = Bitmap.createBitmap(pdfPage.width, pdfPage.height, Bitmap.Config.ARGB_8888)
            pdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

            pdfPage.close()
            pdfRenderer.close()
            parcelFileDescriptor.close()

            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}