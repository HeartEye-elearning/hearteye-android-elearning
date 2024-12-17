package nl.hearteye.elearning.ui.screens.discussions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.model.Discussion
import nl.hearteye.elearning.data.model.DiscussionResponse
import nl.hearteye.elearning.data.model.User
import nl.hearteye.elearning.data.repository.DiscussionRepository
import nl.hearteye.elearning.data.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class DiscussionViewModel @Inject constructor(
    private val discussionRepository: DiscussionRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _discussions = mutableStateOf<List<DiscussionResponse>>(emptyList())
    val discussions: State<List<DiscussionResponse>> = _discussions

    private val _userCache = mutableStateOf<Map<String, User>>(emptyMap())
    val userCache: State<Map<String, User>> = _userCache

    private var currentPage = 0

    fun getDiscussions(page: Int = 0, size: Int = 10, creator: Boolean = false) {
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val discussionsResponse = discussionRepository.getDiscussions(page, size, creator)

                _discussions.value = _discussions.value + discussionsResponse
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An unknown error occurred"
            }
        }
    }

    fun loadMoreDiscussions() {
        currentPage++
        getDiscussions(page = currentPage)
    }

    fun createDiscussion(title: String, content: String, category: String) {
        _errorMessage.value = null
        val discussion = Discussion(title, content, category)

        viewModelScope.launch {
            try {
                discussionRepository.createDiscussion(discussion)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to post discussion: ${e.message}"
            }
        }
    }

    fun getUser(userId: String) {
        if (_userCache.value.containsKey(userId)) return

        viewModelScope.launch {
            try {
                val user = userRepository.getUser(userId)
                _userCache.value = _userCache.value.toMutableMap().apply {
                    put(userId, user)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch user: ${e.message}"
            }
        }
    }
}


