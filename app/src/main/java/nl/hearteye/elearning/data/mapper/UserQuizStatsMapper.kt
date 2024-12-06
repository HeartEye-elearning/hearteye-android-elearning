package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.UserQuizStatsEntity
import nl.hearteye.elearning.data.model.UserQuizStats

object UserQuizStatsMapper {
    fun map(entity: UserQuizStatsEntity): UserQuizStats {
        return UserQuizStats(
            userId = entity.userId,
            quizId = entity.quizId,
            stats = UserQuizStats.Stats(
                score = entity.stats.score,
                numAttempts = entity.stats.numAttempts,
                fastestTime = entity.stats.fastestTime,
                slowestTime = entity.stats.slowestTime,
                averageTime = entity.stats.averageTime
            ),
            attempts = entity.attempts.map { attempt ->
                UserQuizStats.Attempt(
                    id = attempt.id,
                    isFinished = attempt.isFinished,
                    answers = attempt.answers.map { answer ->
                        UserQuizStats.Attempt.Answer(
                            questionId = answer.questionId,
                            givenAnswerId = answer.givenAnswerId,
                            isCorrect = answer.isCorrect
                        )
                    }
                )
            }
        )
    }
}
