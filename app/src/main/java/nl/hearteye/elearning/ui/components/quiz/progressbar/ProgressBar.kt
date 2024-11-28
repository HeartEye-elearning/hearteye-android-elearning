package nl.hearteye.elearning.ui.components.quiz.progressbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.theme.ForegroundPrimary

@Composable
fun ProgressBar(
    currentQuestion: Int,
    totalQuestions: Int,
    progress: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$currentQuestion / $totalQuestions",
            modifier = Modifier.padding(end = 8.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(10.dp)
                .background(Color.Black, shape = RoundedCornerShape(10.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = progress)
                    .background(ForegroundPrimary, shape = RoundedCornerShape(10.dp))
            )
        }
    }
}
