package nl.hearteye.elearning.ui.components.result

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.components.buttons.OutlinedButton
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.components.quiz.answeroverviewcircle.AnswerOverviewCircle
import nl.hearteye.elearning.ui.theme.ForegroundGray
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun ResultOverviewPage(
    onRetryCourse: () -> Unit,
    onCloseCourse: () -> Unit,
    onSeeQuestions: () -> Unit
) {
    val answers = List(10) { it < 7 }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White, shape = RoundedCornerShape(10.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Questions overview",
                style = typography.titleMedium,
                color = ForegroundGray,
                fontWeight = FontWeight.Bold,
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.padding(16.dp),
            ) {
                items(answers.size) { index ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(2.dp)
                    ) {
                        AnswerOverviewCircle(
                            questionNumber = index + 1,
                            isCorrect = answers[index]
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = onRetryCourse,
                text = "Retry Course",
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )

            RegularButton(
                onClick = onCloseCourse,
                text = "Close course",
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "See results →",
                style = typography.bodyLarge,
                color = ForegroundGray,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { onSeeQuestions() }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

