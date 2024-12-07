package nl.hearteye.elearning.data.model

data class UserQuizStats(
    val userId: String,
    val quizId: String,
    val stats: Stats,
    val attempts: List<Attempt>
) {
    data class Stats(
        val score: Int,
        val numAttempts: Int,
        val fastestTime: Int,
        val slowestTime: Int,
        val averageTime: Int
    )

    data class Attempt(
        val id: String,
        val isFinished: Boolean,
        val answers: List<Answer>
    ) {
        data class Answer(
            val questionId: String,
            val givenAnswerId: String,
            val isCorrect: Boolean
        )
    }
}
