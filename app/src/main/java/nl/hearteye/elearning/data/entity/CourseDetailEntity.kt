package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json
import nl.hearteye.elearning.data.model.Content

data class CourseDetailEntity(
    val id: String,
    @Json(name = "title") val title: Map<String, String>,
    @Json(name = "description") val description: Map<String, String>,
    @Json(name = "informationPages") val informationPages: List<InformationPage>,
    @Json(name = "questions") val questions: List<Question>,
    @Json(name = "durationInMinutes") val durationInMinutes: Int,
    @Json(name = "isActive") val isActive: Boolean,
    @Json(name = "isMain") val isMain: Boolean
)

data class InformationPage(
    val id: String,
    @Json(name = "content") val content: Map<String, String>,
    @Json(name = "contentLocations") val contentLocations: List<String>?
)

data class Question(
    val id: String,
    @Json(name = "question") val question: Map<String, String>,
    @Json(name = "answers") val answers: List<Answer>,
    @Json(name = "imageLocation") val imageLocation: String?,
    @Json(name = "fetchedImage") val fetchedImage: Content?,
)

data class Answer(
    val id: String,
    @Json(name = "content") val content: Map<String, String>,
    @Json(name = "correct") val correct: Boolean
)
