package nl.hearteye.elearning.ui.screens.coursedetail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.model.CourseDetail
import nl.hearteye.elearning.data.repository.CourseRepository
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _courseDetail = mutableStateOf<CourseDetail?>(null)
    val courseDetail: State<CourseDetail?> = _courseDetail

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun fetchCourseDetails(courseId: String, language: String = "eng") {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                _courseDetail.value = courseRepository.getCourseDetails(courseId, language)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load course details: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}