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

    fun getCourses(language: String = "eng") {
        viewModelScope.launch {
            val fetchedCourses = courseRepository.getCourses(language)
            _courses.value = fetchedCourses
            _filteredCourses.value = fetchedCourses
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

