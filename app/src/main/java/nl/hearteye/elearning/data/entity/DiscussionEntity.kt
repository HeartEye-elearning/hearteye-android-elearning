package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json

data class DiscussionEntity(
    val title: String,
    val content: String,
    @Json(name = "base64") val base64: Base64Content
)

data class Base64Content(
    val base64: String,
    val contentType: String,
    val title: String
)
