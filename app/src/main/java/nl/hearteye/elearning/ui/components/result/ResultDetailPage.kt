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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.data.entity.QuestionDetailEntity
import nl.hearteye.elearning.data.model.AnswerDetail
import nl.hearteye.elearning.ui.components.quiz.answerbar.AnswerBar
import nl.hearteye.elearning.ui.theme.ForegroundGray
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun ResultDetailPage(
    onBack: () -> Unit,
    questionId: String,
    questionDetails: QuestionDetailEntity?
) {
    if (questionDetails == null) {
        CircularProgressIndicator()
        return
    }

//    val questionText = questionDetails.question["en"] ?: "No question available"  // Extract text for a specific key, e.g., "en"

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
//            Text(
//                text = questionText,  // Now passing a String to Text
//                style = typography.bodyLarge
//            )
        }

        Text(
            text = questionId
        )
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
            questionDetails.answers.forEach { answer ->
                AnswerBar(
                    id = answer.id,
                    answer = answer.content.toString(),
                    isSelected = false,
                    isCorrect = answer.correct,
                    onCheckedChange = { isSelected ->

                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Go back to question overview →",
            style = typography.bodyLarge,
            color = ForegroundGray,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable { onBack() }
        )
    }
}

