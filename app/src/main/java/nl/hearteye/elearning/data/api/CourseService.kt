package nl.hearteye.elearning.data.api

import nl.hearteye.elearning.data.entity.CourseEntity
import retrofit2.http.GET

interface CourseService {
    @GET("quiz-service/v1/quizzes")
    suspend fun getCourses(): List<CourseEntity>
}