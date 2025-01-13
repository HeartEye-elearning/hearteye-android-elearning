package nl.hearteye.elearning.ui.screens.coursedetail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import nl.hearteye.elearning.ui.components.course.InformationPage
import nl.hearteye.elearning.ui.components.course.StartPage
import nl.hearteye.elearning.ui.components.error.ErrorView
import nl.hearteye.elearning.ui.components.quiz.QuestionPage
import nl.hearteye.elearning.ui.theme.ForegroundPrimary

@Composable
fun CourseDetailScreen(
    courseId: String,
    navController: NavHostController,
    courseDetailViewModel: CourseDetailViewModel = hiltViewModel()
) {
    val courseDetail = courseDetailViewModel.courseDetail.value
    val isLoading = courseDetailViewModel.isLoading.value
    val errorMessage = courseDetailViewModel.errorMessage.value

    val currentInformationPageIndex = remember { mutableIntStateOf(0) }
    val currentQuizQuestionIndex = remember { mutableIntStateOf(0) }
    val hasCompletedInformationPages = remember { mutableStateOf(false) }
    val isQuizReady = remember { mutableStateOf(false) }
    val isCourseStarted = remember { mutableStateOf(false) }
    val isQuizCompleted = remember { mutableStateOf(false) }
    val showQuizOverview = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        courseDetailViewModel.fetchCourseDetails(courseId)
        courseDetailViewModel.setQuizId(courseId)
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
                        courseDetailViewModel.fetchCourseDetails(courseId)
                    }
                )
            }

            courseDetail != null -> {
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
                            content = currentInformationPage.content,
                            onNext = { currentInformationPageIndex.intValue++ },
                            onBack = { currentInformationPageIndex.intValue-- },
                            canGoBack = currentInformationPageIndex.intValue > 0,
                            canGoNext = currentInformationPageIndex.intValue < courseDetail.informationPages.size - 1,
                            onStartQuiz = {
                                hasCompletedInformationPages.value = true
                                isQuizReady.value = true
                            },
                            isLastPage = currentInformationPageIndex.intValue == courseDetail.informationPages.size - 1,
                            imageLocations = currentInformationPage.fetchedContent?.firstOrNull()
                        )
                    }
                }

                if (isQuizReady.value && !isQuizCompleted.value) {
                    val currentQuestion =
                        courseDetail.questions.getOrNull(currentQuizQuestionIndex.intValue)
                    if (currentQuestion != null) {
                        key(currentQuizQuestionIndex.intValue) {
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
                                onCompleteQuiz = {
                                    isQuizCompleted.value = true
                                    showQuizOverview.value = true
                                    courseDetailViewModel.finishQuiz(courseId)
                                },
                                image = currentQuestion.fetchedImage
                            )
                        }
                    }
                }

                if (isQuizCompleted.value) {
                    LaunchedEffect(Unit) {
                        navController.navigate("answerOverview/$courseId")
                    }
                }
            }
        }
    }
}
