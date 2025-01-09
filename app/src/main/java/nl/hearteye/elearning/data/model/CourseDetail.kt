package nl.hearteye.elearning.data.model


data class CourseDetail(
    val id: String,
    val title: String,
    val description: String,
    val informationPages: List<InformationPage>,
    val questionCount: Int,
    val durationInMinutes: Int,
    val questions: List<QuestionDetail>
)

data class InformationPage(
    val id: String,
    val content: String,
    val contentLocations: List<String>?,
    var fetchedContent: List<Content>? = null
)

data class QuestionDetail(
    val id: String,
    val question: String,
    val answers: List<AnswerDetail>,
    val imageLocation: String
)

data class AnswerDetail(
    val id: String,
    val content: String,
    val correct: Boolean
)


