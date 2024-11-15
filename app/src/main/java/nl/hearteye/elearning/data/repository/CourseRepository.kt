package nl.hearteye.elearning.data.repository

import nl.hearteye.elearning.data.api.CourseService
import nl.hearteye.elearning.data.mapper.CourseMapper
import nl.hearteye.elearning.data.model.Course
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val courseService: CourseService
) {

    suspend fun getCourses(language: String = "eng"): List<Course> {
        val apiCourses = courseService.getCourses()
        return apiCourses.map { CourseMapper.map(it, language) }
    }
}