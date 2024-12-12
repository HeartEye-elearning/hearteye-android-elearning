package nl.hearteye.elearning.data.model

import nl.hearteye.elearning.data.entity.Base64Content

data class Discussion(
    val title: String,
    val content: String,
    val base64: Base64Content
)

data class Base64Content(
    val base64: String,
    val contentType: String,
    val title: String
)
