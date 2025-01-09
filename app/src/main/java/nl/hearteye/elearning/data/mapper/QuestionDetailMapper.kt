package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.QuestionDetailEntity
import nl.hearteye.elearning.data.model.QuestionDetail
import nl.hearteye.elearning.data.model.AnswerDetail

object QuestionDetailMapper {
    fun map(entity: QuestionDetailEntity, language: String = "eng"): QuestionDetail {
        return QuestionDetail(
            id = entity.id,
            question = entity.question[language] ?: "No question available",
            answers = entity.answers.map { answer ->
                AnswerDetail(
                    id = answer.id,
                    content = answer.content[language] ?: "No answer available",
                    correct = answer.correct,

                    )
            },
            imageLocation = entity.imageLocation,
        )
    }
}
