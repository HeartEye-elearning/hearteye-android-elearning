package nl.hearteye.elearning.ui.components.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun QuizOverview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Quiz Overview",
            fontSize = 24.sp
        )
        Text(
            text = "Congratulations! You have completed the quiz.",
            fontSize = 16.sp
        )
    }
}
