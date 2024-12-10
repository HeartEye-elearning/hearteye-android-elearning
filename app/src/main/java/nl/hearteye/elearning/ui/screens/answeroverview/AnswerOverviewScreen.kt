package nl.hearteye.elearning.ui.screens.answeroverview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import nl.hearteye.elearning.ui.components.result.ResultDetailPage
import nl.hearteye.elearning.ui.components.result.ResultPage
import nl.hearteye.elearning.ui.components.result.ResultOverviewPage

@Composable
fun AnswerOverviewScreen(
    courseId: String,
    viewModel: AnswerOverviewViewModel = hiltViewModel()
) {
    val score by viewModel.userQuizStats.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val questionDetails by viewModel.questionDetails.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.fetchScoreForUser(courseId)
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
                            onRetryCourse = { },
                            onCloseCourse = { },
                            onSeeQuestions = { currentScreen = Screen.ResultOverview }
                        )
                    }

                    Screen.ResultOverview -> {
                        ResultOverviewPage(
                            onRetryCourse = {},
                            onCloseCourse = { },
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
                            questionId = selectedQuestionId ?: "",
                            questionDetails = questionDetails,
                            onBack = {
                                selectedQuestionId = null
                                currentScreen = Screen.ResultOverview
                            }
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