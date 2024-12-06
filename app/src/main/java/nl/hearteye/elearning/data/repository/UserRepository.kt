package nl.hearteye.elearning.data.repository

import nl.hearteye.elearning.data.api.UserService
import nl.hearteye.elearning.data.mapper.UserQuizStatsMapper
import nl.hearteye.elearning.data.model.UserQuizStats
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService
) {

    suspend fun getUserQuizStats(userId: String, quizId: String): UserQuizStats {
        val entity = userService.getUserQuizStats(userId, quizId)
        return UserQuizStatsMapper.map(entity)
    }
}
