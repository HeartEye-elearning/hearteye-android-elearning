package nl.hearteye.elearning.ui.components.pdf

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import nl.hearteye.elearning.R
import nl.hearteye.elearning.ui.utils.renderPdfPage

@Composable
fun PdfViewer(pdfUrl: String, pageIndex: Int = 0) {
    val context = LocalContext.current
    var pdfBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isFullScreen by remember { mutableStateOf(false) }

    LaunchedEffect(pdfUrl, pageIndex) {
        pdfBitmap = renderPdfPage(context, pdfUrl, pageIndex)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (pdfBitmap == null) {
            Image(
                painter = rememberImagePainter(
                    data = R.drawable.placeholder,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.placeholder)
                    }
                ),
                contentDescription = "Course Cover",
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Image(
                bitmap = pdfBitmap!!.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        isFullScreen = true
                    }
            )
        }

        if (isFullScreen) {
            FullScreenPdfViewer(
                pdfUrl = pdfUrl,
                onDismiss = { isFullScreen = false }
            )
        }
    }
}