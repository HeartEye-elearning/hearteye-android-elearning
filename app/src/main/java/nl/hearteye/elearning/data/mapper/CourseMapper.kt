package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.CourseEntity
import nl.hearteye.elearning.data.model.Course

object CourseMapper {
    fun map(entity: CourseEntity, language: String): Course {
        val title = entity.title[language] ?: entity.title["eng"] ?: "Untitled"
        val description = entity.description[language] ?: entity.description["eng"] ?: "No description available"

        return Course(
            id = entity.id,
            title = title,
            description = description,
            duration = entity.durationInMinutes,
            createdAt = entity.createdAt,
            imageLocation = entity.imageLocation,
            isMain = entity.isMain
        )
    }
}
