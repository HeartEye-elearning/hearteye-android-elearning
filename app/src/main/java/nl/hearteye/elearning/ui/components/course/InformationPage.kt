package nl.hearteye.elearning.ui.components.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import nl.hearteye.elearning.data.model.Content
import nl.hearteye.elearning.ui.components.buttons.OutlinedButton
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.components.pdf.PdfViewer
import nl.hearteye.elearning.ui.components.popup.Popup
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun InformationPage(
    title: String,
    content: String,
    onNext: () -> Unit,
    onBack: () -> Unit,
    canGoBack: Boolean,
    canGoNext: Boolean,
    onStartQuiz: () -> Unit,
    isLastPage: Boolean,
    imageLocations: Content?
) {
    val showQuizPopup = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = title,
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = content,
                    style = typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (imageLocations != null && imageLocations.contentType.isNotEmpty()) {
                    when (imageLocations.contentType) {
                        "application/pdf" -> {
                            PdfViewer(pdfUrl = imageLocations.sasUrl)
                        }
                        "image/png", "image/jpeg" -> {
                            Image(
                                painter = rememberImagePainter(data = imageLocations.sasUrl),
                                contentDescription = "Course Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                        }
                        else -> {
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (canGoBack) {
                OutlinedButton(
                    onClick = onBack,
                    text = "Back",
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.width(16.dp))

            if (canGoNext) {
                RegularButton(
                    onClick = onNext,
                    text = "Next",
                    modifier = Modifier.weight(1f)
                )
            }

            if (isLastPage) {
                RegularButton(
                    onClick = { showQuizPopup.value = true },
                    text = "Start Quiz",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    if (showQuizPopup.value) {
        Popup(
            isVisible = showQuizPopup.value,
            title = "Start the Quiz?",
            text = "Are you ready to start the quiz? You will not be able to go back.",
            confirmButtonText = "Start",
            cancelButtonText = "Back",
            onConfirm = {
                onStartQuiz()
                showQuizPopup.value = false
            },
            onCancel = {
                showQuizPopup.value = false
            },
            onDismiss = {
                showQuizPopup.value = false
            }
        )
    }
}