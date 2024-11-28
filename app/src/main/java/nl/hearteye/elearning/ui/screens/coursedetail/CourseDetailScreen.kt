package nl.hearteye.elearning.ui.screens.coursedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.hearteye.elearning.ui.components.InformationPage
import nl.hearteye.elearning.ui.components.QuestionPage
import nl.hearteye.elearning.ui.components.StartPage
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.components.error.ErrorView
import nl.hearteye.elearning.ui.theme.ForegroundPrimary

@Composable
fun CourseDetailScreen(
    courseId: String,
    courseDetailViewModel: CourseDetailViewModel = hiltViewModel()
) {
    val courseDetail = courseDetailViewModel.courseDetail.value
    val isLoading = courseDetailViewModel.isLoading.value
    val errorMessage = courseDetailViewModel.errorMessage.value

    val currentPageIndex = remember { mutableIntStateOf(0) }
    val currentQuestionIndex = remember { mutableIntStateOf(0) }
    val isStarted = remember { mutableStateOf(false) }
    val isInformationPagesDone = remember { mutableStateOf(false) }

    val isCourseStarted = remember { mutableStateOf(false) }

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
                    onRetry = {
                        courseDetailViewModel.fetchCourseDetails(
                            courseId = courseId,
                            language = "eng"
                        )
                    }
                )
            }

            courseDetail != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                        .windowInsetsPadding(WindowInsets.systemBars)
                        .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    if (!isCourseStarted.value) {
                        StartPage(
                            title = courseDetail.title,
                            onStart = {
                                isCourseStarted.value = true
                            }
                        )
                    }

                    if (isCourseStarted.value && !isStarted.value) {
                        val currentInformationPage =
                            courseDetail.informationPages.getOrNull(currentPageIndex.intValue)
                        if (currentInformationPage != null) {
                            InformationPage(
                                title = courseDetail.title,
                                content = currentInformationPage.content["eng"]
                                    ?: "No content available",
                                onNext = {
                                    currentPageIndex.intValue++
                                },
                                onBack = {
                                    currentPageIndex.intValue--
                                },
                                canGoBack = currentPageIndex.intValue > 0,
                                canGoNext = currentPageIndex.intValue < courseDetail.informationPages.size - 1
                            )
                        }

                        if (currentPageIndex.intValue == courseDetail.informationPages.size - 1) {
                            RegularButton(
                                onClick = {
                                    isStarted.value = true
                                    isInformationPagesDone.value = true
                                },
                                text = "Start Quiz",
                                modifier = Modifier.width(140.dp)
                            )
                        }
                    }

                    if (isStarted.value) {
                        val currentQuestion =
                            courseDetail.questions.getOrNull(currentQuestionIndex.intValue)
                        if (currentQuestion != null) {
                            QuestionPage(
                                question = currentQuestion,
                                currentQuestionIndex = currentQuestionIndex.intValue,
                                totalQuestions = courseDetail.questions.size,
                                onNext = {
                                    if (currentQuestionIndex.intValue < courseDetail.questions.size - 1) {
                                        currentQuestionIndex.intValue++
                                    }
                                },
                                onBack = {
                                    if (currentQuestionIndex.intValue > 0) {
                                        currentQuestionIndex.intValue--
                                    }
                                },
                                canGoBack = currentQuestionIndex.intValue > 0,
                                canGoNext = currentQuestionIndex.intValue < courseDetail.questions.size - 1
                            )
                        }
                    }
                }
            }
        }
    }
}
