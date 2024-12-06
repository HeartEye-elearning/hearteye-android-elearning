package nl.hearteye.elearning.ui.screens.answeroverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.repository.UserRepository
import nl.hearteye.elearning.data.model.UserQuizStats
import javax.inject.Inject

@HiltViewModel
class AnswerOverviewViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _score = MutableStateFlow<Int?>(null)
    val score: StateFlow<Int?> = _score

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchScoreForUser(userId: String, quizId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val stats: UserQuizStats = userRepository.getUserQuizStats(userId, quizId)
                _score.value = stats.stats.score
            } catch (e: Exception) {
                _error.value = "Failed to load score: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
