package nl.hearteye.elearning.ui.screens.discussions

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.model.Discussion
import nl.hearteye.elearning.data.model.DiscussionResponse
import nl.hearteye.elearning.data.repository.DiscussionRepository
import javax.inject.Inject

@HiltViewModel
class DiscussionViewModel @Inject constructor(
    private val discussionRepository: DiscussionRepository,
) : ViewModel() {

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _discussions = mutableStateOf<List<DiscussionResponse>>(emptyList())
    val discussions: State<List<DiscussionResponse>> = _discussions

    // Correctly handle the response and map it into the model
    fun getDiscussions(page: Int = 0, size: Int = 10, creator: Boolean = false) {
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val discussionsResponse = discussionRepository.getDiscussions(page, size, creator)

                // Update the _discussions value to hold the correct data
                _discussions.value = listOf(discussionsResponse) // Wrapping it in a list since discussionsResponse is a DiscussionResponse, not a list
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An unknown error occurred"
            }
        }
    }

    fun createDiscussion(title: String, content: String, category: String) {
        _errorMessage.value = null
        val discussion = Discussion(title, content, category)

        viewModelScope.launch {
            try {
                discussionRepository.createDiscussion(discussion)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to post discussion: ${e.message}"
            }
        }
    }
}
