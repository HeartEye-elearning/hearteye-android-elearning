package nl.hearteye.elearning.ui.screens.home

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

    private val _courseErrorMessage = mutableStateOf<String?>(null)
    val courseErrorMessage: State<String?> = _courseErrorMessage

    private val _discussionsErrorMessage = mutableStateOf<String?>(null)
    val discussionsErrorMessage: State<String?> = _discussionsErrorMessage

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    private val _isCoursesLoading = mutableStateOf(false)
    val isCoursesLoading: State<Boolean> = _isCoursesLoading

    private val _isDiscussionsLoading = mutableStateOf(false)
    val isDiscussionsLoading: State<Boolean> = _isDiscussionsLoading


    fun getCourses() {
        _isCoursesLoading.value = true
        _courseErrorMessage.value = null
        viewModelScope.launch {
            try {
                val savedLanguage = dataStoreManager.getSelectedLanguage() ?: "eng"
                val fetchedCourses = courseRepository.getCourses(savedLanguage)

                val updatedCourses = fetchedCourses.map { course ->
                    if (course.imageLocation != null) {
                        try {
                            val fetchedContentEntity = contentRepository.getContent(course.imageLocation.toString())
                            val fetchedContent = ContentMapper.map(fetchedContentEntity)

                            course.copy(imageContent = fetchedContent.sasUrl)
                        } catch (e: Exception) {
                            _courseErrorMessage.value = "Error fetching content: ${e.message}"
                            course
                        }
                    } else {
                        course
                    }
                }
                _courses.value = updatedCourses
            } catch (e: Exception) {
                _courseErrorMessage.value = e.message ?: "An unknown error occurred"
            } finally {
                _isCoursesLoading.value = false
            }
        }
    }

    fun getDiscussions(
        page: Int = 0,
        size: Int = 3,
        creator: Boolean = false,
        search: String? = null,
        category: String? = null,
    ) {
        _isDiscussionsLoading.value = true
        _discussionsErrorMessage.value = null
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

                            discussion.copy(imageLocation = content.sasUrl)
                        } catch (e: Exception) {
                            _discussionsErrorMessage.value = "Error fetching content: ${e.message}"
                            discussion
                        }
                    } else {
                        discussion
                    }
                }

                _discussions.value = listOf(discussionsResponse.copy(content = updatedDiscussions))
            } catch (e: Exception) {
                _discussionsErrorMessage.value = e.message ?: "An unknown error occurred"
            } finally {
                _isDiscussionsLoading.value = false
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
