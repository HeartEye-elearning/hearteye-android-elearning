package nl.hearteye.elearning.ui.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.model.Course
import nl.hearteye.elearning.data.model.DiscussionResponse
import nl.hearteye.elearning.data.repository.CourseRepository
import nl.hearteye.elearning.data.repository.DiscussionRepository
import nl.hearteye.elearning.data.store.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val dataStoreManager: DataStoreManager,
    private val discussionRepository: DiscussionRepository,
) : ViewModel() {

    private val _courses = mutableStateOf<List<Course>>(emptyList())
    val courses: State<List<Course>> = _courses

    private val _discussions = mutableStateOf<List<DiscussionResponse>>(emptyList())
    val discussions: State<List<DiscussionResponse>> = _discussions

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun getCourses() {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val savedLanguage = dataStoreManager.getSelectedLanguage() ?: "eng"
                val fetchedCourses = courseRepository.getCourses(savedLanguage)
                _courses.value = fetchedCourses
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load courses: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private var currentPage = 0

    fun getDiscussions(
        page: Int = 0,
        size: Int = 3,
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
}
