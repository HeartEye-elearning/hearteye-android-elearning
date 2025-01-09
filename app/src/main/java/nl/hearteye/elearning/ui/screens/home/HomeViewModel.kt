package nl.hearteye.elearning.ui.screens.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.mapper.ContentMapper
import nl.hearteye.elearning.data.model.Course
import nl.hearteye.elearning.data.model.DiscussionResponse
import nl.hearteye.elearning.data.model.User
import nl.hearteye.elearning.data.repository.ContentRepository
import nl.hearteye.elearning.data.repository.CourseRepository
import nl.hearteye.elearning.data.repository.DiscussionRepository
import nl.hearteye.elearning.data.repository.UserRepository
import nl.hearteye.elearning.data.store.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val dataStoreManager: DataStoreManager,
    private val discussionRepository: DiscussionRepository,
    private val userRepository: UserRepository,
    private val contentRepository: ContentRepository,
) : ViewModel() {

    private val _courses = mutableStateOf<List<Course>>(emptyList())
    val courses: State<List<Course>> = _courses

    private val _discussions = mutableStateOf<List<DiscussionResponse>>(emptyList())
    val discussions: State<List<DiscussionResponse>> = _discussions

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    fun getCourses() {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val savedLanguage = dataStoreManager.getSelectedLanguage() ?: "eng"
                val fetchedCourses = courseRepository.getCourses(savedLanguage)

                val updatedCourses = fetchedCourses.map { course ->
                    val imageContent = if (course.imageLocation != null) {
                        val fetchedContentEntity = contentRepository.getContent(course.imageLocation.toString())
                        val fetchedContent = ContentMapper.map(fetchedContentEntity)
                        fetchedContent.sasUrl
                    } else {
                        null
                    }

                    course.copy(imageContent = imageContent)
                }

                _courses.value = updatedCourses
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load courses: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private var currentPage = 0

    fun getDiscussions(
        page: Int = 0, // Always fetch the first page
        size: Int = 3, // Fetch exactly 3 discussions
        creator: Boolean = false,
        search: String? = null,
        category: String? = null,
    ) {
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val uppercaseCategory = category?.uppercase()

                val discussionsResponse =
                    discussionRepository.getDiscussions(page, size, creator, search, uppercaseCategory)

                val updatedDiscussions = discussionsResponse.content.map { discussion ->
                    if (discussion.fileLocation != null) {
                        try {
                            val contentEntity = contentRepository.getContent(discussion.fileLocation)
                            val content = ContentMapper.map(contentEntity)
                            Log.d("Discussions", "sasUrl: ${content.sasUrl}")
                            discussion.copy(imageLocation = content.sasUrl)
                        } catch (e: Exception) {
                            _errorMessage.value = "Error fetching content: ${e.message}"
                            discussion
                        }
                    } else {
                        discussion
                    }
                }

                _discussions.value = listOf(discussionsResponse.copy(content = updatedDiscussions))
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An unknown error occurred"
            }
        }
    }

    fun fetchCurrentUser() {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val user = userRepository.getCurrentUser()
                _currentUser.value = user
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch current user: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
