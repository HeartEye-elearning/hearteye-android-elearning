package nl.hearteye.elearning.data.repository

import nl.hearteye.elearning.data.api.CourseService
import nl.hearteye.elearning.data.entity.QuestionDetailEntity
import nl.hearteye.elearning.data.mapper.CourseDetailMapper
import nl.hearteye.elearning.data.mapper.CourseMapper
import nl.hearteye.elearning.data.model.Course
import nl.hearteye.elearning.data.model.CourseDetail
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val courseService: CourseService
) {

    suspend fun getCourses(language: String = "eng"): List<Course> {
        val apiCourses = courseService.getCourses()
        return apiCourses.map { CourseMapper.map(it, language) }
    }

    suspend fun getCourseDetails(courseId: String, language: String = "eng"): CourseDetail {
        val apiCourseDetail = courseService.getCourseDetails(courseId)
        return CourseDetailMapper.map(apiCourseDetail, language)
    }

    suspend fun submitAnswer(quizId: String, questionId: String, answerId: String) {
        courseService.giveAnswer(
            quizId = quizId,
            questionId = questionId,
            answerId = mapOf("givenAnswerId" to answerId)
        )
    }

    suspend fun getQuestionDetails(quizId: String, questionId: String): QuestionDetailEntity {
        return courseService.getQuestionDetails(quizId, questionId)
    }
}
