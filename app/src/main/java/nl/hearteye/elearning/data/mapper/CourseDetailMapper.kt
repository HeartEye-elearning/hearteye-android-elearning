package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.CourseDetailEntity
import nl.hearteye.elearning.data.entity.InformationPage as InformationPageEntity
import nl.hearteye.elearning.data.entity.Question as QuestionEntity
import nl.hearteye.elearning.data.entity.Answer as AnswerEntity
import nl.hearteye.elearning.data.model.AnswerDetail
import nl.hearteye.elearning.data.model.CourseDetail
import nl.hearteye.elearning.data.model.QuestionDetail
import nl.hearteye.elearning.data.model.InformationPage

object CourseDetailMapper {

    fun map(entity: CourseDetailEntity, language: String): CourseDetail {
        return CourseDetail(
            id = entity.id,
            title = getLocalizedText(entity.title, language),
            description = getLocalizedText(entity.description, language),
            informationPages = entity.informationPages.map { mapInformationPage(it, language) },
            questions = entity.questions.map { mapQuestionDetail(it, language) },
            questionCount = entity.questions.size,
            durationInMinutes = entity.durationInMinutes
        )
    }

    private fun mapInformationPage(entity: InformationPageEntity, language: String): InformationPage {
        return InformationPage(
            id = entity.id,
            content = getLocalizedText(entity.content, language)
        )
    }

    private fun mapQuestionDetail(entity: QuestionEntity, language: String): QuestionDetail {
        return QuestionDetail(
            id = entity.id,
            question = getLocalizedText(entity.question, language),
            answers = entity.answers.map { mapAnswerDetail(it, language) }
        )
    }

    private fun mapAnswerDetail(entity: AnswerEntity, language: String): AnswerDetail {
        return AnswerDetail(
            id = entity.id,
            content = getLocalizedText(entity.content, language),
            correct = entity.correct
        )
    }

    private fun getLocalizedText(map: Map<String, String>, language: String): String {
        return map[language] ?: map["eng"] ?: "No content available"
    }
}
