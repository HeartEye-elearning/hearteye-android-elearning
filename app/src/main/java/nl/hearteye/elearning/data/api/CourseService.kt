package nl.hearteye.elearning.data.api

import nl.hearteye.elearning.data.entity.CourseDetailEntity
import nl.hearteye.elearning.data.entity.CourseEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface CourseService {
    @GET("quiz-service/v1/quizzes")
    suspend fun getCourses(): List<CourseEntity>

    @GET("quiz-service/v1/quizzes/{id}")
    suspend fun getCourseDetails(@Path("id") courseId: String): CourseDetailEntity
}