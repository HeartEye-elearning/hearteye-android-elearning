package nl.hearteye.elearning.ui.screens.answeroverview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.hearteye.elearning.ui.components.result.ResultOverviewPage
import nl.hearteye.elearning.ui.components.result.ResultPage


@Composable
fun AnswerOverviewScreen(courseId: String) {
    Box(modifier = Modifier.fillMaxSize()) {
//        ResultPage(
//            score = 78,
//            onRetryCourse = { /* Navigate to retry logic */ },
//            onCloseCourse = { /* Navigate to course close */ },
//            onSeeQuestions = { /* Navigate to questions */ }
//        )
        ResultOverviewPage(
            onRetryCourse = { /* Navigate to retry logic */ },
            onCloseCourse = { /* Navigate to course close */ },
            onSeeQuestions = { /* Navigate to questions */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultOverviewPagePreview() {
    MaterialTheme {
        AnswerOverviewScreen("1")
    }
}