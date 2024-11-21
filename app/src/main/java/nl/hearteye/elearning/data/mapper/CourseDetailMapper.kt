package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.CourseDetailEntity
import nl.hearteye.elearning.data.entity.InformationPage
import nl.hearteye.elearning.data.model.CourseDetail

object CourseDetailMapper {
    fun map(entity: CourseDetailEntity, language: String = "eng"): CourseDetail {
        return CourseDetail(
            id = entity.id,
            title = entity.title[language] ?: "Untitled",
            informationPages = entity.informationPages.map {
                InformationPage(
                    id = it.id,
                    content = it.content
                )
            },
            questionCount = entity.questions.size
        )
    }
}
