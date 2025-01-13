package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json

data class DiscussionEntity(
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "base64") val base64: Base64ContentEntity,
    @Json(name = "category") val category: String,
    @Json(name = "profileImage") val profileImage: String?
)

data class Base64ContentEntity(
    @Json(name = "base64") val base64: String,
    @Json(name = "contentType") val contentType: String,
    @Json(name = "title") val title: String
)
