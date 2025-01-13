package nl.hearteye.elearning.ui.screens.courses

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.mapper.ContentMapper
import nl.hearteye.elearning.data.model.Course
import nl.hearteye.elearning.data.model.Content
import nl.hearteye.elearning.data.repository.CourseRepository
import nl.hearteye.elearning.data.repository.ContentRepository
import nl.hearteye.elearning.data.store.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val contentRepository: ContentRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _courses = mutableStateOf<List<Course>>(emptyList())
    val courses: State<List<Course>> = _courses

    private val _filteredCourses = mutableStateOf<List<Course>>(emptyList())
    val filteredCourses: State<List<Course>> = _filteredCourses

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
                _filteredCourses.value = updatedCourses
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load courses: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterCourses(query: String) {
        _filteredCourses.value = if (query.isEmpty()) {
            _courses.value
        } else {
            _courses.value.filter { course ->
                course.title.contains(query, ignoreCase = true)
            }
        }
    }
}
