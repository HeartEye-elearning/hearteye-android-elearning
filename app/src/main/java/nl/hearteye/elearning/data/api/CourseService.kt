package nl.hearteye.elearning.data.api

import nl.hearteye.elearning.data.entity.CourseDetailEntity
import nl.hearteye.elearning.data.entity.CourseEntity
import nl.hearteye.elearning.data.entity.QuestionDetailEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CourseService {
    @GET("quiz-service/v1/quizzes")
    suspend fun getCourses(): List<CourseEntity>

    @GET("quiz-service/v1/quizzes/{id}")
    suspend fun getCourseDetails(@Path("id") courseId: String): CourseDetailEntity

    @POST("quiz-service/v1/quizzes/{id}/questions/{questionId}/give-answer")
    suspend fun giveAnswer(
        @Path("id") quizId: String,
        @Path("questionId") questionId: String,
        @Body answerId: Map<String, String>
    )

    @GET("quiz-service/v1/quizzes/{quizId}/questions/{questionId}")
    suspend fun getQuestionDetails(
        @Path("quizId") quizId: String,
        @Path("questionId") questionId: String
    ): QuestionDetailEntity

    @PUT("quiz-service/v1/quizzes/{id}/finish")
    suspend fun finishQuiz(
        @Path("id") courseId: String
    ): Response<Unit>
}
