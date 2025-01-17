package nl.hearteye.elearning.data.model

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val duration: Int,
    val createdAt: String,
    val imageLocation: String?,
    var imageContent: String? = null,
    val isMain: Boolean
)