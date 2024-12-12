package nl.hearteye.elearning.ui.screens.discussions

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.entity.Base64Content
import nl.hearteye.elearning.data.model.Discussion
import nl.hearteye.elearning.data.repository.DiscussionRepository
import javax.inject.Inject

@HiltViewModel
class DiscussionViewModel @Inject constructor(
    private val discussionRepository: DiscussionRepository,
) : ViewModel() {

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage


    fun createDiscussion(
        title: String,
        content: String,
        base64Content: String,
        contentType: String,
        base64Title: String
    ) {
        _errorMessage.value = null
        val base64 = Base64Content(base64Content, contentType, base64Title)
        val discussion = Discussion(title, content, base64)

        viewModelScope.launch {
            try {
                discussionRepository.createDiscussion(discussion)

            } catch (e: Exception) {
                _errorMessage.value = "Failed to post discussion: ${e.message}"
            }
        }
    }
}