package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.CourseDetailEntity
import nl.hearteye.elearning.data.entity.InformationPage
import nl.hearteye.elearning.data.model.AnswerDetail
import nl.hearteye.elearning.data.model.CourseDetail
import nl.hearteye.elearning.data.model.QuestionDetail

object CourseDetailMapper {
    fun map(entity: CourseDetailEntity, language: String): CourseDetail {
        return CourseDetail(
            id = entity.id,
            title = entity.title[language] ?: "Untitled",
            description = entity.description[language] ?: "No description available",
            informationPages = entity.informationPages.map {
                InformationPage(
                    id = it.id,
                    content = it.content,
                )
            },
            questions = entity.questions.map {
                QuestionDetail(
                    id = it.id,
                    question = it.question[language] ?: "No question available",
                    answers = it.answers.map { answer ->
                        AnswerDetail(
                            id = answer.id,
                            content = answer.content[language] ?: "No answer available",
                            correct = answer.correct
                        )
                    },
                )
            },
            questionCount = entity.questions.size,
            durationInMinutes = entity.durationInMinutes
        )
    }
}
