package nl.hearteye.elearning.ui.screens.coursedetail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.model.CourseDetail
import nl.hearteye.elearning.data.repository.ContentRepository
import nl.hearteye.elearning.data.repository.CourseRepository
import nl.hearteye.elearning.data.store.DataStoreManager
import nl.hearteye.elearning.data.entity.ContentEntity
import nl.hearteye.elearning.data.mapper.ContentMapper
import nl.hearteye.elearning.data.model.Content
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val contentRepository: ContentRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _courseDetail = mutableStateOf<CourseDetail?>(null)
    val courseDetail: State<CourseDetail?> = _courseDetail

    private val _content = mutableStateOf<ContentEntity?>(null)
    val content: State<ContentEntity?> = _content

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun fetchCourseDetails(courseId: String) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val savedLanguage = dataStoreManager.getSelectedLanguage() ?: "eng"
                val courseDetail = courseRepository.getCourseDetails(courseId, savedLanguage)

                courseDetail.informationPages.forEach { page ->
                    if (!page.contentLocations.isNullOrEmpty()) {
                        val fetchedContents = page.contentLocations.mapNotNull { location ->
                            try {
                                contentRepository.getContent(location)?.let { ContentMapper.map(it) }
                            } catch (e: Exception) {
                                _errorMessage.value = "Failed to load content: ${e.message}"
                                null
                            }
                        }
                        page.fetchedContent = fetchedContents
                    } else {
                        page.fetchedContent = emptyList()
                    }
                }

                _courseDetail.value = courseDetail
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load course details: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }



    fun submitAnswer(quizId: String, questionId: String, answerId: String) {
        viewModelScope.launch {
            try {
                courseRepository.submitAnswer(quizId, questionId, answerId)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to submit answer: ${e.message}"
            }
        }
    }

    fun finishQuiz(quizId: String) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                courseRepository.finishQuiz(quizId)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to finish quiz: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
