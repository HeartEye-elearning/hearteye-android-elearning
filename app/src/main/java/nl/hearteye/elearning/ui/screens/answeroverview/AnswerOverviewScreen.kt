package nl.hearteye.elearning.ui.screens.answeroverview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nl.hearteye.elearning.ui.components.pdf.result.ResultDetailPage
import nl.hearteye.elearning.ui.components.pdf.result.ResultPage
import nl.hearteye.elearning.ui.components.pdf.result.ResultOverviewPage
import nl.hearteye.elearning.ui.navigation.NavRoutes

@Composable
fun AnswerOverviewScreen(
    courseId: String,
    navController: NavController,
    viewModel: AnswerOverviewViewModel = hiltViewModel()
) {
    val score by viewModel.userQuizStats.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val questionDetails by viewModel.questionDetails.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    var selectedLanguage by remember { mutableStateOf("eng") }

    LaunchedEffect(Unit) {
        viewModel.fetchCurrentUser()
    }

    LaunchedEffect(currentUser) {
        currentUser?.let {
            viewModel.fetchScoreForUser(courseId)
            selectedLanguage = viewModel.getSelectedLanguage()
        }
    }

    var selectedQuestionId by remember { mutableStateOf<String?>(null) }
    var currentScreen by remember { mutableStateOf(Screen.ResultPage) }


    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            error != null -> {
                Text(
                    text = error.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            score != null -> {
                when (currentScreen) {
                    Screen.ResultPage -> {
                        ResultPage(
                            score = score?.stats?.score ?: 0,
                            onRetryCourse = {
                                viewModel.clearQuizData()
                                viewModel.fetchScoreForUser(courseId)
                            },
                            onCloseCourse = {
                                viewModel.clearQuizData()
                                navController.navigate(NavRoutes.COURSES.route) {
                                    popUpTo(NavRoutes.COURSES.route) { inclusive = true }
                                }
                            },
                            onSeeQuestions = { currentScreen = Screen.ResultOverview }
                        )
                    }

                    Screen.ResultOverview -> {
                        ResultOverviewPage(
                            onRetryCourse = {
                                viewModel.clearQuizData()
                            },
                            onCloseCourse = {
                                viewModel.clearQuizData()
                                navController.navigate(NavRoutes.COURSES.route) {
                                    popUpTo(NavRoutes.COURSES.route) { inclusive = true }
                                }
                            },
                            onSeeQuestions = { currentScreen = Screen.ResultPage },
                            userQuizStats = score!!,
                            onCircleClick = { questionId ->
                                selectedQuestionId = questionId
                                viewModel.fetchQuestionDetails(courseId, questionId)
                                currentScreen = Screen.ResultDetail
                            }
                        )
                    }

                    Screen.ResultDetail -> {
                        ResultDetailPage(
                            questionDetails = questionDetails,
                            onBack = {
                                selectedQuestionId = null
                                currentScreen = Screen.ResultOverview
                            },
                            language = selectedLanguage
                        )
                    }
                }
            }
        }
    }
}

private enum class Screen {
    ResultPage,
    ResultOverview,
    ResultDetail
}

