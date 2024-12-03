package nl.hearteye.elearning.ui.components.result

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.data.model.AnswerDetail
import nl.hearteye.elearning.ui.components.quiz.answerbar.AnswerBar
import nl.hearteye.elearning.ui.theme.ForegroundGray
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun ResultDetailPage(
    onBack: () -> Unit
) {
    val questionText =
        "What is a characteristic of the HeartEye-ECG due to the placement of the four electrodes directly above the heart?"

    val answers = listOf(
        AnswerDetail(id = "a1", content = "The P-waves are less visible", correct = true),
        AnswerDetail(id = "a2", content = "The QRS-axis is horizontally left-centered", correct = false),
        AnswerDetail(id = "a3", content = "The STT segments change more slowly and less pronounced.", correct = false),
        AnswerDetail(id = "a4", content = "The anterior leads show no R-wave progression.", correct = false)
    )

    val selectedAnswerId = "a1"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Text(
                text = questionText,
                style = typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                answers.forEach { answer ->
                    AnswerBar(
                        id = answer.id,
                        answer = answer.content,
                        isSelected = selectedAnswerId == answer.id,
                        isCorrect = answer.correct,
                        onCheckedChange = { isSelected ->

                        }
                    )
                }
            }

            Text(
                text = "Go back to question overview →",
                style = typography.bodyLarge,
                color = ForegroundGray,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { onBack() }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}




