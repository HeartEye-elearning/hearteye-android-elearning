package nl.hearteye.elearning.data.model

import nl.hearteye.elearning.data.entity.Base64Content

data class Discussion(
    val title: String,
    val content: String,
    val category: String
)

data class Base64Content(
    val base64: String,
    val contentType: String,
    val title: String
)
