package nl.hearteye.elearning.ui.screens.coursedetail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.model.AnswerResponse
import nl.hearteye.elearning.data.model.CourseDetail
import nl.hearteye.elearning.data.model.QuestionResult
import nl.hearteye.elearning.data.repository.CourseRepository
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _courseDetail = mutableStateOf<CourseDetail?>(null)
    val courseDetail: State<CourseDetail?> = _courseDetail

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _answerFeedback = MutableLiveData<AnswerResponse?>()
    val answerFeedback: LiveData<AnswerResponse?> = _answerFeedback

    private val _questionResults = mutableStateOf<List<QuestionResult>>(emptyList())
    val questionResults: State<List<QuestionResult>> = _questionResults

    fun fetchCourseDetails(courseId: String, language: String = "eng") {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                _courseDetail.value = courseRepository.getCourseDetails(courseId, language)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load course details: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun submitAnswer(quizId: String, questionId: String, answerId: String) {
        viewModelScope.launch {
            try {
                val response = courseRepository.submitAnswer(quizId, questionId, answerId)
                _answerFeedback.value = response

                // Update the question results
                val updatedResults = _questionResults.value.toMutableList()
                updatedResults.add(
                    QuestionResult(
                        questionId = questionId,
                        answerId = answerId,
                        isCorrect = response.isCorrect
                    )
                )
                _questionResults.value = updatedResults
            } catch (e: Exception) {
                _errorMessage.value = "Failed to submit answer: ${e.message}"
            }
        }
    }
}

