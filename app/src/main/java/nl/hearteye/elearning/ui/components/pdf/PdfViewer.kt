package nl.hearteye.elearning.ui.components.pdf

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import nl.hearteye.elearning.ui.utils.renderPdfPage

@Composable
fun PdfViewer(pdfUrl: String, pageIndex: Int = 0) {
    val context = LocalContext.current
    var pdfBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isFullScreen by remember { mutableStateOf(false) }

    LaunchedEffect(pdfUrl, pageIndex) {
        pdfBitmap = renderPdfPage(context, pdfUrl, pageIndex)
    }

    // When user clicks the image, toggle fullscreen
    pdfBitmap?.let { bitmap ->
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    isFullScreen = true // Open the PDF in full-screen mode
                }
        )
    }

    // Full-screen view in landscape mode
    if (isFullScreen) {
        FullScreenPdfViewer(
            pdfUrl = pdfUrl,
            onDismiss = { isFullScreen = false } // Close the full-screen view
        )
    }
}