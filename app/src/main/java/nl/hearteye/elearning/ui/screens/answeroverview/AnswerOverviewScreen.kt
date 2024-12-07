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


    // Hardcoded
    val userId = "8dab58ae-8ddb-4674-b8db-602c39a258cf"
    val courseId = "8dab58ae-8dce-4674-b8db-602c39a258cf"

    LaunchedEffect(Unit) {
        viewModel.fetchScoreForUser(userId, courseId)
    }

    var showResultPage by remember { mutableStateOf(true) }

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
                if (showResultPage) {
                    ResultPage(
                        score = score?.stats?.score ?: 0,
                        onRetryCourse = { /* Navigate to retry logic */ },
                        onCloseCourse = { /* Navigate to course close */ },
                        onSeeQuestions = { showResultPage = false }
                    )
                } else {
                    ResultOverviewPage(
                        onRetryCourse = { /* Navigate to retry logic */ },
                        onCloseCourse = { /* Navigate to course close */ },
                        onSeeQuestions = { showResultPage = true },
                        userQuizStats = score!! 
                    )
                }
            }
        }
    }
}
