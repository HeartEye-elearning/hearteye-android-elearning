import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.hearteye.elearning.data.model.User
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography
import nl.hearteye.elearning.R
import nl.hearteye.elearning.data.model.DiscussionDetail
import nl.hearteye.elearning.ui.screens.discussions.DiscussionViewModel

@Composable
fun DiscussionsCard(
    user: User,
    postTime: String,
    postTitle: String,
    postContent: String,
    ecgImageResId: Int,
    discussionId: String,
    isExpanded: Boolean,
    onReadMoreClick: (String) -> Unit,
    discussionDetail: DiscussionDetail?,
    isCurrentUser: Boolean,
    discussionViewModel: DiscussionViewModel = hiltViewModel(),
) {
    val ecgImage: Painter = painterResource(id = ecgImageResId)
    val timeAgo = getTimeAgo(postTime)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            Image(
                painter = ecgImage,
                contentDescription = "ECG Image",
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Black, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    style = typography.titleMedium,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = timeAgo,
                    style = MaterialTheme.typography.bodySmall,
                )

                Spacer(modifier = Modifier.weight(1f))

                if (isCurrentUser) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More Options",
                        tint = ForegroundPrimary,
                        modifier = Modifier
                            .graphicsLayer(rotationZ = 90f)
                            .clickable {
                                discussionViewModel.deleteDiscussion(discussionId)
                            }
                    )
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = postTitle,
                style = typography.titleMedium,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
            )

            if (isExpanded) {
                Text(
                    text = discussionDetail?.content ?: postContent,
                    style = typography.bodyMedium,
                )

                Row(
                    modifier = Modifier
                        .padding(top = 4.dp)
                ) {
                    discussionDetail?.let { detail ->
                        val commentCount = detail.comments.size
                        Text(
                            text = "$commentCount Comments",
                            style = typography.bodyMedium,
                            color = ForegroundPrimary,
                            modifier = Modifier
                                .weight(1f)
                        )
                    }

                    Text(
                        text = "View less",
                        style = typography.bodyMedium,
                        color = ForegroundPrimary,
                        modifier = Modifier.clickable {
                            onReadMoreClick(discussionId)
                        }
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = postContent,
                        style = typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Read more",
                        style = typography.bodyMedium,
                        color = ForegroundPrimary,
                        modifier = Modifier.clickable {
                            onReadMoreClick(discussionId)
                        }
                    )
                }
            }
        }
    }
}


