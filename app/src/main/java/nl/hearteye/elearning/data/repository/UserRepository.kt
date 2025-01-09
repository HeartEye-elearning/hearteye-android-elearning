package nl.hearteye.elearning.data.repository

import nl.hearteye.elearning.data.api.UserService
import nl.hearteye.elearning.data.entity.ProfilePictureEntity
import nl.hearteye.elearning.data.mapper.UserMapper
import nl.hearteye.elearning.data.mapper.UserQuizStatsMapper
import nl.hearteye.elearning.data.model.User
import nl.hearteye.elearning.data.model.UserQuizStats
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService
) {

    suspend fun getUserQuizStats(userId: String, quizId: String): UserQuizStats {
        val entity = userService.getUserQuizStats(userId, quizId)
        return UserQuizStatsMapper.map(entity)
    }

    suspend fun getCurrentUser(): User {
        val userEntity = userService.getCurrentUser()
        return UserMapper.map(userEntity)
    }

    suspend fun getUser(userId: String): User {
        val userEntity = userService.getUser(userId)
        return UserMapper.map(userEntity)
    }

    suspend fun updateProfilePicture(id: String, request: ProfilePictureEntity): Response<Unit> {
        return userService.updateProfilePicture(id, request)
    }
}
