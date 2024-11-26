package nl.hearteye.elearning.ui.screens.coursedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.hearteye.elearning.ui.components.InformationPage
import nl.hearteye.elearning.ui.components.QuestionPage
import nl.hearteye.elearning.ui.components.error.ErrorView
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun CourseDetailScreen(
    courseId: String,
    courseDetailViewModel: CourseDetailViewModel = hiltViewModel()
) {
    val courseDetail = courseDetailViewModel.courseDetail.value
    val isLoading = courseDetailViewModel.isLoading.value
    val errorMessage = courseDetailViewModel.errorMessage.value

    val currentPageIndex = remember { mutableStateOf(0) }
    val currentQuestionIndex = remember { mutableStateOf(0) }
    val isStarted = remember { mutableStateOf(false) }
    val isInformationPagesDone = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        courseDetailViewModel.fetchCourseDetails(courseId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = ForegroundPrimary
                )
            }
            errorMessage != null -> {
                ErrorView(
                    message = errorMessage,
                    onRetry = { courseDetailViewModel.fetchCourseDetails(courseId = courseId, language = "eng") }
                )
            }
            courseDetail != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                        .padding(16.dp)
                ) {
                    if (!isStarted.value) {
                        val currentInformationPage = courseDetail.informationPages.getOrNull(currentPageIndex.value)
                        if (currentInformationPage != null) {
                            InformationPage(
                                title = courseDetail.title,
                                content = currentInformationPage.content["eng"] ?: "No content available",
                                onNext = {
                                    currentPageIndex.value++
                                },
                                onBack = {
                                    currentPageIndex.value--
                                },
                                canGoBack = currentPageIndex.value > 0,
                                canGoNext = currentPageIndex.value < courseDetail.informationPages.size - 1
                            )
                        }

                        if (currentPageIndex.value == courseDetail.informationPages.size - 1) {
                            Button(
                                onClick = {
                                    isStarted.value = true
                                    isInformationPagesDone.value = true
                                },
                                modifier = Modifier.width(140.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = ForegroundPrimary)
                            ) {
                                Text(text = "Start Quiz", color = Color.White, style = typography.bodyLarge)
                            }
                        }
                    }

                    if (isStarted.value) {
                        val currentQuestion = courseDetail.questions.getOrNull(currentQuestionIndex.value)
                        if (currentQuestion != null) {
                            QuestionPage(
                                question = currentQuestion,
                                onNext = {
                                    if (currentQuestionIndex.value < courseDetail.questions.size - 1) {
                                        currentQuestionIndex.value++
                                    }
                                },
                                onBack = {
                                    if (currentQuestionIndex.value > 0) {
                                        currentQuestionIndex.value--
                                    }
                                },
                                canGoBack = currentQuestionIndex.value > 0,
                                canGoNext = currentQuestionIndex.value < courseDetail.questions.size - 1
                            )
                        }
                    }
                }
            }
        }
    }
}


