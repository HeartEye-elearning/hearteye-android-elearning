package nl.hearteye.elearning.ui.components.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.data.model.QuestionResult

@Composable
fun QuizOverview(questionResults: List<QuestionResult>) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Quiz Overview")
        if (questionResults.isNotEmpty()) {
            questionResults.forEachIndexed { index, result ->
                AnswerOverviewCircle(
                    questionNumber = index + 1,
                    isCorrect = result.isCorrect
                )
            }
        } else {
            Text(text = "empty")
        }
    }
}