package nl.hearteye.elearning.ui.components.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import getTimeAgo
import nl.hearteye.elearning.data.model.Comment
import nl.hearteye.elearning.data.model.DiscussionDetail
import nl.hearteye.elearning.data.model.User
import nl.hearteye.elearning.ui.screens.discussions.DiscussionViewModel
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun CommentsOverlay(
    discussionDetail: DiscussionDetail?,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .wrapContentSize(Alignment.Center)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentSize()
        ) {
            Text(text = "Comments", style = typography.titleMedium)

            discussionDetail?.comments?.let { comments ->
                val groupedComments = comments.groupBy { it.parentCommentId }

                groupedComments[null]?.forEach { comment ->
                    RenderComment(comment)
                    RenderSubcomments(groupedComments, comment.id)
                }
            }
        }
    }
}

@Composable
fun RenderComment(comment: Comment) {
    val user = remember { mutableStateOf<User?>(null) }
    val discussionViewModel: DiscussionViewModel = hiltViewModel()

    if (user.value == null) {
        LaunchedEffect(comment.userId) {
            discussionViewModel.getUser(comment.userId)
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
            text = getTimeAgo(comment.createdAt),
            style = typography.bodySmall
        )
    }

    Text(
        text = comment.content,
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


@Composable
fun RenderSubcomments(groupedComments: Map<String?, List<Comment>>, parentCommentId: String) {
    groupedComments[parentCommentId]?.forEach { subcomment ->
        RenderSubcomment(subcomment)
        RenderSubcomments(groupedComments, subcomment.id)
    }
}

@Composable
fun RenderSubcomment(subcomment: Comment) {
    val user = remember { mutableStateOf<User?>(null) }
    val discussionViewModel: DiscussionViewModel = hiltViewModel()

    if (user.value == null) {
        LaunchedEffect(subcomment.userId) {
            discussionViewModel.getUser(subcomment.userId)
            user.value = discussionViewModel.userCache.value[subcomment.userId]
        }
    }

    user.value?.let {

        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = "${it.firstName} ${it.lastName}",

                style = typography.titleSmall
            )
        }

        Text(
            text = getTimeAgo(subcomment.createdAt),
            style = typography.bodySmall
        )
    }

    Text(
        text = subcomment.content,
        modifier = Modifier
            .padding(top = 8.dp)
            .padding(start = 16.dp),
        style = typography.bodyMedium
    )
    Text(
        text = "Respond",
        modifier = Modifier
            .padding(top = 4.dp)
            .padding(start = 16.dp),
        style = typography.bodySmall,
        color = ForegroundPrimary
    )
}
