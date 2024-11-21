package nl.hearteye.elearning.ui.components.coursecard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.R
import nl.hearteye.elearning.ui.theme.BackgroundBlue
import nl.hearteye.elearning.ui.theme.ForegroundBlue
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun CourseCard(
    title: String,
    time: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable {onClick()}
            .width(300.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(CornerSize(10.dp)),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 4.dp,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                        clip = false
                    )
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.quiz_cover),
                    contentDescription = "Course Cover",
                    modifier = Modifier
                        .height(100.dp)
                        .width(133.dp)
                )
            }

            Row {
                Text(
                    text = title,
                    style = typography.bodyLarge,
                    color = Color.Black
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = BackgroundBlue,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Schedule,
                            contentDescription = "Time Icon",
                            modifier = Modifier
                                .width(14.dp)
                                .height(14.dp),
                            tint = ForegroundBlue
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${time}min",
                            style = typography.bodySmall,
                            color = ForegroundBlue
                        )
                    }
                }
            }
        }
    }
}
