package nl.hearteye.elearning.data.model

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val profilePictureLocation: String?,
    val role: String,
    val profilePicture: String?
)