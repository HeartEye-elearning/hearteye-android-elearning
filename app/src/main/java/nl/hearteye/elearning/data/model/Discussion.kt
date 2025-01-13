package nl.hearteye.elearning.data.model


data class Discussion(
    val title: String,
    val content: String,
    val base64Content: Base64Content,
    val category: String,
    val profileImage: String?
)

data class Base64Content(
    val base64: String,
    val contentType: String,
    val title: String
)
