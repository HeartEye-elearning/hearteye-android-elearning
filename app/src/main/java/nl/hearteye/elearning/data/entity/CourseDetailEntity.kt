package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json

data class CourseDetailEntity(
    val id: String,
    @Json(name = "title") val title: Map<String, String>,
    @Json(name = "description") val description: Map<String, String>,
    @Json(name = "informationPages") val informationPages: List<InformationPage>,
    @Json(name = "questions") val questions: List<Question>,
    @Json(name = "durationInMinutes") val durationInMinutes: Int,
    @Json(name = "isActive") val isActive: Boolean
)

data class InformationPage(
    val id: String,
    @Json(name = "content") val content: Map<String, String>
)

data class Question(
    val id: String,
    @Json(name = "question") val question: Map<String, String>,
    @Json(name = "answers") val answers: List<Answer>
)

data class Answer(
    val id: String,
    @Json(name = "content") val content: Map<String, String>,
    @Json(name = "correct") val correct: Boolean
)
