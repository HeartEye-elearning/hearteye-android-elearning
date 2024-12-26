package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json

data class DiscussionDetailEntity(
    @Json(name = "id") val id: String,
    @Json(name = "userId") val userId: String,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "category") val category: String,
    @Json(name = "comments") val comments: List<CommentEntity>,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "updatedAt") val updatedAt: String
)

data class CommentEntity(
    @Json(name = "id") val id: String,
    @Json(name = "userId") val userId: String,
    @Json(name = "parentCommentId") val parentCommentId: String?,
    @Json(name = "level") val level: Int,
    @Json(name = "content") val content: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "updatedAt") val updatedAt: String
)
