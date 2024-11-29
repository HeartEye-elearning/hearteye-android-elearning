package nl.hearteye.elearning.ui.components.quiz.answeroverviewcircle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun AnswerOverviewCircle(questionNumber: Int, isCorrect: Boolean) {
    val backgroundColor = if (isCorrect) Color(0xFF089E53) else Color(0xFFF89FA0)
    val borderColor = if (isCorrect) Color.Green else Color.Red
    val textColor = if (isCorrect) Color.Green else Color.Red

    Box(
        modifier = Modifier
            .size(75.dp)
            .background(backgroundColor, CircleShape)
            .border(2.dp, borderColor, CircleShape)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = questionNumber.toString(),
            style = typography.headlineSmall,
            color = textColor,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

