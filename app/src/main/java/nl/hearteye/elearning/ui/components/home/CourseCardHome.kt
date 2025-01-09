package nl.hearteye.elearning.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import coil.compose.rememberImagePainter
import nl.hearteye.elearning.R
import nl.hearteye.elearning.ui.theme.BackgroundBlue
import nl.hearteye.elearning.ui.theme.ForegroundBlue
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun CourseCardHome(
    title: String,
    time: String,
    onClick: () -> Unit,
    image: String,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
            .width(200.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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
                    painter = rememberImagePainter(image),
                    contentDescription = "Course Cover",
                    modifier = Modifier.height(135.dp).fillMaxWidth(),

                )
            }

            Text(
                text = title,
                style = typography.bodyLarge,
                color = Color.Black,
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = BackgroundBlue,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Schedule,
                            contentDescription = "Time Icon",
                            modifier = Modifier
                                .size(14.dp),
                            tint = ForegroundBlue
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$time min",
                            style = typography.bodySmall,
                            color = ForegroundBlue
                        )
                    }
                }
            }
        }
    }
}

