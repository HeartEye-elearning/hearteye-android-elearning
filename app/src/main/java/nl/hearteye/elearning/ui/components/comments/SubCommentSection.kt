package nl.hearteye.elearning.ui.components.comments

import androidx.compose.runtime.Composable
import nl.hearteye.elearning.data.model.Comment


@Composable
fun SubCommentSection(groupedComments: Map<String?, List<Comment>>, parentCommentId: String) {
    groupedComments[parentCommentId]?.forEach { subcomment ->
        SubComment(subcomment)
        SubCommentSection(groupedComments, subcomment.id.toString())
    }
}

