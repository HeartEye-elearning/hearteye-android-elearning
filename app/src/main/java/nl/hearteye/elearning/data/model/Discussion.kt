package nl.hearteye.elearning.data.model


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
