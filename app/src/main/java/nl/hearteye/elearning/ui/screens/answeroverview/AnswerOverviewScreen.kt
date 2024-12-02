package nl.hearteye.elearning.ui.screens.answeroverview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.hearteye.elearning.ui.components.result.ResultPage

@Composable
fun AnswerOverviewScreen(courseId: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        ResultPage(
            score = 78,
            onRetryCourse = { /* Navigate to retry logic */ },
            onCloseCourse = { /* Navigate to course close */ },
            onSeeQuestions = { /* Navigate to questions */ }
        )
    }
}