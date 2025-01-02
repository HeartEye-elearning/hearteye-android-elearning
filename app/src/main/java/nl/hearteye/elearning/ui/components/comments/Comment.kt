package nl.hearteye.elearning.ui.components.comments

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import getTimeAgo
import nl.hearteye.elearning.data.model.Comment
import nl.hearteye.elearning.data.model.User
import nl.hearteye.elearning.ui.screens.discussions.DiscussionViewModel
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography


@Composable
fun Comment(comment: Comment) {
    val user = remember { mutableStateOf<User?>(null) }
    val discussionViewModel: DiscussionViewModel = hiltViewModel()

    if (user.value == null) {
        LaunchedEffect(comment.userId) {
            discussionViewModel.getUser(comment.userId.toString())
            user.value = discussionViewModel.userCache.value[comment.userId]
        }
    }

    Row(
        modifier = Modifier.padding(top = 8.dp),
    ) {
        user.value?.let {
            Text(
                text = "${it.firstName} ${it.lastName}",
                style = typography.titleSmall
            )
        }

        Text(
            text = getTimeAgo(comment.createdAt.toString()),
            style = typography.bodySmall,
            color = Color.Gray
        )
    }

    Text(
        text = comment.content.toString(),
        modifier = Modifier.padding(top = 8.dp),
        style = typography.bodyMedium
    )
    Text(
        text = "Respond",
        modifier = Modifier.padding(top = 4.dp),
        style = typography.bodySmall,
        color = ForegroundPrimary
    )
}


