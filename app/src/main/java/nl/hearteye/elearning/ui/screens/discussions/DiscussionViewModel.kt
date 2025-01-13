package nl.hearteye.elearning.ui.screens.discussions

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.mapper.ContentMapper
import nl.hearteye.elearning.data.model.Base64Content
import nl.hearteye.elearning.data.model.Comment
import nl.hearteye.elearning.data.model.Discussion
import nl.hearteye.elearning.data.model.DiscussionDetail
import nl.hearteye.elearning.data.model.DiscussionResponse
import nl.hearteye.elearning.data.model.User
import nl.hearteye.elearning.data.repository.ContentRepository
import nl.hearteye.elearning.data.repository.DiscussionRepository
import nl.hearteye.elearning.data.repository.UserRepository
import nl.hearteye.elearning.ui.utils.convertPdfToBase64
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DiscussionViewModel @Inject constructor(
    private val discussionRepository: DiscussionRepository,
    private val userRepository: UserRepository,
    private val contentRepository: ContentRepository
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

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    private var currentPage = 0

    fun getDiscussions(
        page: Int = 0,
        size: Int = 1,
        creator: Boolean = false,
        search: String? = null,
        category: String? = null,
    ) {
        _errorMessage.value = null
        if (page == 0) {
            _discussions.value = emptyList()
        }
        viewModelScope.launch {
            try {
                val uppercaseCategory = category
                    ?.replace(" ", "_")
                    ?.uppercase()

                val discussionsResponse =
                    discussionRepository.getDiscussions(page, size, creator, search, uppercaseCategory)

                val updatedDiscussions = discussionsResponse.content.map { discussion ->
                    if (discussion.fileLocation != null) {
                        try {
                            val contentEntity = contentRepository.getContent(discussion.fileLocation)
                            val content = ContentMapper.map(contentEntity)
                            discussion.copy(imageLocation = content.sasUrl)

                        } catch (e: Exception) {
                            _errorMessage.value = "Error fetching content: ${e.message}"
                            discussion
                        }
                    } else {
                        discussion
                    }
                }

                _discussions.value = if (page == 0) {
                    listOf(discussionsResponse.copy(content = updatedDiscussions))
                } else {
                    _discussions.value + discussionsResponse.copy(content = updatedDiscussions)
                }

                if (page == 0) currentPage = 0 else currentPage++

            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An unknown error occurred"
            }
        }
    }

    fun createDiscussion(
        title: String,
        content: String,
        category: String,
        pdfFile: File,
        onSuccess: () -> Unit
    ) {
        _errorMessage.value = null

        if (!pdfFile.exists()) {
            _errorMessage.value = "PDF file does not exist."
            return
        }
        if (!pdfFile.canRead()) {
            _errorMessage.value = "Cannot read the PDF file."
            return
        }

        val base64String = convertPdfToBase64(pdfFile) ?: ""
        if (base64String.isEmpty()) {
            _errorMessage.value = "Failed to encode the file to Base64."
            return
        }

        val formattedCategory = category.uppercase().replace(" ", "_")

        val base64Content = Base64Content(base64String, "application/pdf", pdfFile.name)
        val discussion = Discussion(title, content, base64Content, formattedCategory, null)

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
                val contentEntity = contentRepository.getContent(user.profilePictureLocation.toString())
                val content = ContentMapper.map(contentEntity)
                val updatedUser = user.copy(profilePicture = content.sasUrl)
                _userCache.value = _userCache.value.toMutableMap().apply {
                    put(userId, updatedUser)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch user: ${e.message}"
            }
        }
    }



    fun fetchCurrentUser() {
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val user = userRepository.getCurrentUser()
                _currentUser.value = user
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch current user: ${e.message}"
            } finally {
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

