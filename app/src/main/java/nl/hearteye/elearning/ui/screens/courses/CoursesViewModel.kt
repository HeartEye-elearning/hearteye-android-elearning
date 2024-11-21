package nl.hearteye.elearning.ui.screens.courses

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.repository.CourseRepository
import nl.hearteye.elearning.data.model.Course
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _courses = mutableStateOf<List<Course>>(emptyList())
    val courses: State<List<Course>> = _courses

    private val _filteredCourses = mutableStateOf<List<Course>>(emptyList())
    val filteredCourses: State<List<Course>> = _filteredCourses

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun getCourses(language: String = "eng") {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val fetchedCourses = courseRepository.getCourses(language)
                _courses.value = fetchedCourses
                _filteredCourses.value = fetchedCourses
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


