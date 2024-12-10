package nl.hearteye.elearning.ui.screens.answeroverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.entity.QuestionDetailEntity
import nl.hearteye.elearning.data.repository.UserRepository
import nl.hearteye.elearning.data.model.UserQuizStats
import nl.hearteye.elearning.data.repository.CourseRepository
import javax.inject.Inject

@HiltViewModel
class AnswerOverviewViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _userQuizStats = MutableStateFlow<UserQuizStats?>(null)
    val userQuizStats: StateFlow<UserQuizStats?> = _userQuizStats

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _questionDetails = MutableStateFlow<QuestionDetailEntity?>(null)
    val questionDetails: StateFlow<QuestionDetailEntity?> = _questionDetails

    fun fetchScoreForUser(quizId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val stats: UserQuizStats = userRepository.getUserQuizStats(quizId)
                _userQuizStats.value = stats
            } catch (e: Exception) {
                _error.value = "Failed to load score: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchQuestionDetails(quizId: String, questionId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val question = courseRepository.getQuestionDetails(quizId, questionId)
                _questionDetails.value = question
            } catch (e: Exception) {
                _error.value = "Failed to load question details: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}



