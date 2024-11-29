package nl.hearteye.elearning.ui.screens.coursedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.hearteye.elearning.data.model.QuestionResult
import nl.hearteye.elearning.ui.components.course.InformationPage
import nl.hearteye.elearning.ui.components.quiz.QuestionPage
import nl.hearteye.elearning.ui.components.course.StartPage
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.components.error.ErrorView
import nl.hearteye.elearning.ui.components.quiz.QuizOverview
import nl.hearteye.elearning.ui.theme.ForegroundPrimary

@Composable
fun CourseDetailScreen(
    courseId: String,
    courseDetailViewModel: CourseDetailViewModel = hiltViewModel()
) {
    val courseDetail = courseDetailViewModel.courseDetail.value
    val isLoading = courseDetailViewModel.isLoading.value
    val errorMessage = courseDetailViewModel.errorMessage.value
    val answerFeedback = courseDetailViewModel.answerFeedback.value
    val questionResults = courseDetailViewModel.questionResults.value

    val currentInformationPageIndex = remember { mutableIntStateOf(0) }
    val currentQuizQuestionIndex = remember { mutableIntStateOf(0) }
    val hasCompletedInformationPages = remember { mutableStateOf(false) }
    val isQuizReady = remember { mutableStateOf(false) }
    val isCourseStarted = remember { mutableStateOf(false) }
    val showQuizOverview = remember { mutableStateOf(false) }

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
                        courseDetailViewModel.fetchCourseDetails(courseId, "eng")
                    }
                )
            }

            courseDetail != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                        .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                ) {
                    if (!isCourseStarted.value) {
                        StartPage(
                            title = courseDetail.title,
                            onStart = { isCourseStarted.value = true }
                        )
                    }

                    if (isCourseStarted.value && !hasCompletedInformationPages.value) {
                        val currentInformationPage =
                            courseDetail.informationPages.getOrNull(currentInformationPageIndex.intValue)
                        if (currentInformationPage != null) {
                            InformationPage(
                                title = courseDetail.title,
                                content = currentInformationPage.content["eng"] ?: "No content available",
                                onNext = { currentInformationPageIndex.intValue++ },
                                onBack = { currentInformationPageIndex.intValue-- },
                                canGoBack = currentInformationPageIndex.intValue > 0,
                                canGoNext = currentInformationPageIndex.intValue < courseDetail.informationPages.size - 1
                            )
                        }

                        if (currentInformationPageIndex.intValue == courseDetail.informationPages.size - 1) {
                            RegularButton(
                                onClick = {
                                    hasCompletedInformationPages.value = true
                                    isQuizReady.value = true
                                },
                                text = "Start Quiz",
                                modifier = Modifier.width(140.dp)
                            )
                        }
                    }

                    if (isQuizReady.value) {
                        val currentQuestion =
                            courseDetail.questions.getOrNull(currentQuizQuestionIndex.intValue)
                        if (currentQuestion != null) {
                            QuestionPage(
                                question = currentQuestion,
                                currentQuestionIndex = currentQuizQuestionIndex.intValue,
                                totalQuestions = courseDetail.questions.size,
                                onNext = {
                                    if (currentQuizQuestionIndex.intValue < courseDetail.questions.size - 1) {
                                        currentQuizQuestionIndex.intValue++
                                    }
                                },
                                onBack = {
                                    if (currentQuizQuestionIndex.intValue > 0) {
                                        currentQuizQuestionIndex.intValue--
                                    }
                                },
                                canGoBack = currentQuizQuestionIndex.intValue > 0,
                                canGoNext = currentQuizQuestionIndex.intValue < courseDetail.questions.size - 1,
                                onSubmitAnswer = { answerId ->
                                    courseDetailViewModel.submitAnswer(
                                        quizId = courseDetail.id,
                                        questionId = currentQuestion.id,
                                        answerId = answerId
                                    )
                                },
                                answerFeedback = answerFeedback,
                                onCompleteQuiz = {
                                    showQuizOverview.value = true
                                }
                            )
                        }

                        if (showQuizOverview.value) {
                            QuizOverview(questionResults)
                        }
                    }
                }
            }
        }
    }
}



