package nl.hearteye.elearning.ui.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.data.model.QuestionDetail
import nl.hearteye.elearning.ui.components.answerbar.AnswerBar
import nl.hearteye.elearning.ui.components.buttons.OutlinedButton
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun QuestionPage(
    question: QuestionDetail,
    onNext: () -> Unit,
    onBack: () -> Unit,
    canGoBack: Boolean,
    canGoNext: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = question.question,
            style = typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        question.answers.forEach { answer ->
            AnswerBar(id = answer.id, answer = answer.content, isSelected = false) {}
        }

        Column {
            AnswerBar(
                id = "1",
                answer = "The QRS-axis is horizontally left-centered",
                isSelected = false,
                onCheckedChange = {}
            )
            AnswerBar(
                id = "2",
                answer = "The P-waves are less visible",
                isSelected = true,
                onCheckedChange = {}
            )
            AnswerBar(
                id = "3",
                answer = "The QRS-axis is horizontally right-centered",
                isSelected = false,
                onCheckedChange = {}
            )
            AnswerBar(
                id = "4",
                answer = "The P-waves are more visible",
                isSelected = false,
                onCheckedChange = {}
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
                    text = "Next"
                )
            }
        }
    }
}


