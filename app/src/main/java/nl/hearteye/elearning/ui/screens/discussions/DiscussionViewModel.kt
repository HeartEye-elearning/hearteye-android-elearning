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

    fun getDiscussions() {
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val discussionList = discussionRepository.getDiscussions()
                Log.d("DiscussionViewModel", "Fetched discussions size: ${discussionList.size}")

                _discussions.value = discussionList
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch discussions: ${e.message}"
            }
        }
    }

    fun createDiscussion(
        title: String,
        content: String,
        category: String
    ) {
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
