package nl.hearteye.elearning.ui.screens.discussions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.model.Comment
import nl.hearteye.elearning.data.model.Discussion
import nl.hearteye.elearning.data.model.DiscussionDetail
import nl.hearteye.elearning.data.model.DiscussionResponse
import nl.hearteye.elearning.data.model.User
import nl.hearteye.elearning.data.repository.DiscussionRepository
import nl.hearteye.elearning.data.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class DiscussionViewModel @Inject constructor(
    private val discussionRepository: DiscussionRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _discussions = mutableStateOf<List<DiscussionResponse>>(emptyList())
    val discussions: State<List<DiscussionResponse>> = _discussions

    private val _userCache = mutableStateOf<Map<String, User>>(emptyMap())
    val userCache: State<Map<String, User>> = _userCache

    private val _discussionDetail = mutableStateOf<DiscussionDetail?>(null)
    val discussionDetail: State<DiscussionDetail?> = _discussionDetail

    val _searchQuery = mutableStateOf<String>("")
    val searchQuery: State<String> = _searchQuery

    private var currentPage = 0

    fun getDiscussions(
        page: Int = 0,
        size: Int = 2,
        creator: Boolean = false,
        search: String? = null
    ) {
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val discussionsResponse =
                    discussionRepository.getDiscussions(page, size, creator, search)

                if (search != null) {
                    _discussions.value = listOf(discussionsResponse)
                    currentPage = 0
                } else {
                    _discussions.value = _discussions.value + discussionsResponse
                    currentPage++
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An unknown error occurred"
            }
        }
    }

    fun createDiscussion(title: String, content: String, category: String, onSuccess: () -> Unit) {
        _errorMessage.value = null
        val discussion = Discussion(title, content, category)

        viewModelScope.launch {
            try {
                discussionRepository.createDiscussion(discussion)
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to post discussion: ${e.message}"
            }
        }
    }

    fun getUser(userId: String) {
        _errorMessage.value = null

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

    fun getDiscussionById(discussionId: String) {
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val discussionDetail = discussionRepository.getDiscussionById(discussionId)
                _discussionDetail.value = discussionDetail
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch discussion details: ${e.message}"
            }
        }
    }

    private val _deleteResult = mutableStateOf<Result<Boolean>>(Result.success(false))
    val deleteResult: State<Result<Boolean>> = _deleteResult

    fun deleteDiscussion(discussionId: String) {
        viewModelScope.launch {
            _deleteResult.value = Result.success(false)

            try {
                val response = discussionRepository.deleteDiscussion(discussionId)
                if (response.isSuccessful) {
                    _deleteResult.value = Result.success(true)
                    getDiscussions()
                } else {
                    _deleteResult.value = Result.failure(Exception("Failed to delete"))
                }
            } catch (e: Exception) {
                _deleteResult.value = Result.failure(e)
            }
        }
    }

    fun fetchCurrentUser() {
        viewModelScope.launch {
            try {
                val user = userRepository.getCurrentUser()
                _userCache.value = _userCache.value.toMutableMap().apply {
                    put(user.id, user)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch current user: ${e.message}"
            }
        }
    }

    fun createComment(discussionId: String, commentText: String, parentCommentId: String? = null) {
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val comment = Comment(
                    content = commentText,
                    id = null,
                    userId = null,
                    parentCommentId = parentCommentId,
                    level = null,
                    createdAt = null,
                    updatedAt = null
                )
                discussionRepository.createComment(discussionId, comment)

                getDiscussionById(discussionId)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to add comment: ${e.message}"
            }
        }
    }

}

