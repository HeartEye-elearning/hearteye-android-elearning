package nl.hearteye.elearning.data.api

import nl.hearteye.elearning.data.entity.UserEntity
import nl.hearteye.elearning.data.entity.UserQuizStatsEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("user-service/v1/users/{id}/quizzes/{quizId}/stats")
    suspend fun getUserQuizStats(
        @Path("id") id: String,
        @Path("quizId") quizId: String
    ): UserQuizStatsEntity

    @GET("user-service/v1/users/me")
    suspend fun getCurrentUser(
    ): UserEntity

    @GET("user-service/v1/users/{id}")
    suspend fun getUser(
        @Path("id") id: String,
    ): UserEntity
}



