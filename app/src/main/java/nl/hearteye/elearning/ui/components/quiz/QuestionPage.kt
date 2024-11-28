package nl.hearteye.elearning.ui.components.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.data.model.QuestionDetail
import nl.hearteye.elearning.ui.components.quiz.answerbar.AnswerBar
import nl.hearteye.elearning.ui.components.buttons.OutlinedButton
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.components.quiz.progressbar.ProgressBar
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun QuestionPage(
    question: QuestionDetail,
    currentQuestionIndex: Int,
    totalQuestions: Int,
    onNext: () -> Unit,
    onBack: () -> Unit,
    canGoBack: Boolean,
    canGoNext: Boolean
) {
    val selectedAnswer = remember { mutableStateOf<String?>(null) }
    val progress = (currentQuestionIndex + 1).toFloat() / totalQuestions

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ProgressBar(
            currentQuestion = currentQuestionIndex + 1,
            totalQuestions = totalQuestions,
            progress = progress
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = question.question,
            style = typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        question.answers.forEach { answer ->
            AnswerBar(
                id = answer.id,
                answer = answer.content,
                isSelected = selectedAnswer.value == answer.id,
                onCheckedChange = { isSelected ->
                    if (isSelected) {
                        selectedAnswer.value = answer.id
                    } else {
                        selectedAnswer.value = null
                    }
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (canGoBack) {
                OutlinedButton(
                    onClick = onBack,
                    text = "Back"
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            if (canGoNext) {
                RegularButton(
                    onClick = onNext,
                    text = "Next",
                    enabled = selectedAnswer.value != null
                )
            }
        }
    }
}




