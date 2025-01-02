package nl.hearteye.elearning.ui.components.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.data.model.DiscussionDetail
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun CommentsOverlay(
    discussionDetail: DiscussionDetail?,
    onClose: () -> Unit,
    onAddComment: (String) -> Unit
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
                    Comment(comment)
                    SubCommentSection(groupedComments, comment.id.toString())
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CommentInput(onAddComment)
        }
    }
}


