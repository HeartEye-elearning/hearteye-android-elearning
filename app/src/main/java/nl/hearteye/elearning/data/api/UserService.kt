package nl.hearteye.elearning.data.api

import nl.hearteye.elearning.data.entity.ProfilePictureEntity
import nl.hearteye.elearning.data.entity.UserEntity
import nl.hearteye.elearning.data.entity.UserQuizStatsEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
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

    @PUT("user-service/v1/users/{id}/profile-picture")
    suspend fun updateProfilePicture(
        @Path("id") id: String,
        @Body request: ProfilePictureEntity
    ): Response<Unit>
}



