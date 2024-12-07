package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json

data class UserQuizStatsEntity(
    @Json(name = "userId") val userId: String,
    @Json(name = "quizId") val quizId: String,
    @Json(name = "stats") val stats: Stats,
    @Json(name = "attempts") val attempts: List<Attempt>
) {
    data class Stats(
        @Json(name = "score") val score: Int,
        @Json(name = "numAttempts") val numAttempts: Int,
        @Json(name = "fastestTime") val fastestTime: Int,
        @Json(name = "slowestTime") val slowestTime: Int,
        @Json(name = "averageTime") val averageTime: Int
    )

    data class Attempt(
        @Json(name = "id") val id: String,
        @Json(name = "isFinished") val isFinished: Boolean,
        @Json(name = "answers") val answers: List<Answer>
    ) {
        data class Answer(
            @Json(name = "questionId") val questionId: String,
            @Json(name = "givenAnswerId") val givenAnswerId: String,
            @Json(name = "isCorrect") val isCorrect: Boolean
        )
    }
}
