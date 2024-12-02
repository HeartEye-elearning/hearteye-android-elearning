package nl.hearteye.elearning.ui.components.result

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import nl.hearteye.elearning.ui.theme.ForegroundGray
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun ResultPage(
    score: Int,
    onRetryCourse: () -> Unit,
    onCloseCourse: () -> Unit,
    onSeeQuestions: () -> Unit
) {
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
                text = "Results",
                style = typography.titleMedium,
                color = ForegroundGray,
                fontWeight = FontWeight.Bold,
            )
            Box(
                modifier = Modifier.padding(top = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(70.dp),
                    progress = score / 100f,
                    color = ForegroundPrimary,
                    strokeWidth = 10.dp,

                )
                Text(
                    text = "$score/100",
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green
                )
            }

            Text(
                text = "Grade",
                style = typography.bodyLarge,
                color = ForegroundGray,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Congratulations",
                style = typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = ForegroundGray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFA726))
                    .padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Medal",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                )
            }

            Text(
                text = "You have completed the course with a score of $score/100.",
                style = typography.bodyLarge,
                color = ForegroundGray,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

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
                text = "See questions →",
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

