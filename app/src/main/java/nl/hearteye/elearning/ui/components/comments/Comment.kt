package nl.hearteye.elearning.ui.components.comments

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import getTimeAgo
import nl.hearteye.elearning.R
import nl.hearteye.elearning.data.model.Comment
import nl.hearteye.elearning.data.model.User
import nl.hearteye.elearning.ui.screens.discussions.DiscussionViewModel
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography


@Composable
fun Comment(comment: Comment, onRespondClick: (Comment) -> Unit) {
    val user = remember { mutableStateOf<User?>(null) }
    val discussionViewModel: DiscussionViewModel = hiltViewModel()

    if (user.value == null) {
        LaunchedEffect(comment.userId) {
            discussionViewModel.getUser(comment.userId.toString())
            user.value = discussionViewModel.userCache.value[comment.userId]
        }
    }

    Row(
        modifier = Modifier
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        user.value?.let {
            Image(
                painter = rememberImagePainter(it.profilePicture ?: R.drawable.profile_picture_placeholder,
                ),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(25.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "${it.firstName} ${it.lastName}",
                style = typography.titleSmall,
            )
        }

        Text(
            text = getTimeAgo(comment.createdAt.toString()),
            style = typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(start = 8.dp)
        )
    }

    Text(
        text = comment.content.toString(),
        modifier = Modifier
            .padding(top = 6.dp),
        style = typography.bodyMedium
    )

    Text(
        text = "Respond",
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable { onRespondClick(comment) },
        style = typography.bodySmall,
        color = ForegroundPrimary
    )
}

