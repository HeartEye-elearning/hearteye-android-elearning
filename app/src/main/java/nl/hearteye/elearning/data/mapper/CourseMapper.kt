package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.CourseEntity
import nl.hearteye.elearning.data.model.Course

object CourseMapper {
    fun map(entity: CourseEntity, language: String): Course {
        return Course(
            id = entity.id,
            title = entity.title[language] ?: "Untitled",
            description = entity.description[language] ?: "No description available",
            duration = entity.durationInMinutes,
            createdAt = entity.createdAt
        )
    }
}