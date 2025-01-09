package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json

data class QuestionDetailEntity(
    val id: String,
    @Json(name = "question") val question: Map<String, String>,
    @Json(name = "answers") val answers: List<AnswerEntity>,
    @Json(name = "imageLocation") val imageLocation: String
)

data class AnswerEntity(
    val id: String,
    @Json(name = "content") val content: Map<String, String>,
    @Json(name = "correct") val correct: Boolean
)
