package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json
import nl.hearteye.elearning.data.model.Content

data class QuestionDetailEntity(
    val id: String,
    @Json(name = "question") val question: Map<String, String>,
    @Json(name = "answers") val answers: List<AnswerEntity>,
    @Json(name = "imageLocation") val imageLocation: String?,
    @Json(name = "fetchedImage") val fetchedImage: Content?,
)

data class AnswerEntity(
    val id: String,
    @Json(name = "content") val content: Map<String, String>,
    @Json(name = "correct") val correct: Boolean
)
