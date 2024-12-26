package nl.hearteye.elearning.data.model

data class DiscussionDetail(
    val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val category: String,
    val comments: List<Comment>,
    val createdAt: String,
    val updatedAt: String
)

data class Comment(
    val id: String,
    val userId: String,
    val parentCommentId: String?,
    val level: Int,
    val content: String,
    val createdAt: String,
    val updatedAt: String
)