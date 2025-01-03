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
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun SubComment(subcomment: Comment) {
    val user = remember { mutableStateOf<User?>(null) }
    val discussionViewModel: DiscussionViewModel = hiltViewModel()

    if (user.value == null) {
        LaunchedEffect(subcomment.userId) {
            discussionViewModel.getUser(subcomment.userId.toString())
            user.value = discussionViewModel.userCache.value[subcomment.userId]
        }
    }

    user.value?.let {

        Row(modifier = Modifier.padding(top = 12.dp)) {
            Text(
                text = "${it.firstName} ${it.lastName}",
                style = typography.titleSmall,
                modifier = Modifier.padding(start = 32.dp)
            )
            Text(
                text = getTimeAgo(subcomment.createdAt.toString()),
                style = typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }

    Text(
        text = subcomment.content.toString(),
        modifier = Modifier
            .padding(top = 6.dp)
            .padding(start = 32.dp, end = 16.dp, bottom = 8.dp),
        style = typography.bodyMedium
    )
}
