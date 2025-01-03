package nl.hearteye.elearning.ui.components.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.data.model.Comment
import nl.hearteye.elearning.data.model.DiscussionDetail
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun CommentsOverlay(
    discussionDetail: DiscussionDetail?,
    onClose: () -> Unit,
    onAddComment: (String, Comment?) -> Unit
) {
    val selectedComment = remember { mutableStateOf<Comment?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(enabled = true) { onClose() }
            .wrapContentSize(Alignment.Center)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(500.dp)
                .clickable(enabled = false) {}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Comments",
                    style = typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                discussionDetail?.comments?.let { comments ->
                    val groupedComments = comments.groupBy { it.parentCommentId }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        groupedComments[null]?.forEach { comment ->
                            item {
                                Comment(
                                    comment = comment,
                                    onRespondClick = { selectedComment.value = comment }
                                )
                                SubCommentSection(groupedComments, comment.id.toString())
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                CommentInput(
                    onAddComment = { text ->
                        onAddComment(text, selectedComment.value)
                        selectedComment.value = null
                    },
                    selectedComment = selectedComment.value,
                    onClearSelectedComment = { selectedComment.value = null }
                )
            }
        }
    }
}




