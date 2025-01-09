package nl.hearteye.elearning.ui.screens.more

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.entity.ProfilePictureEntity
import nl.hearteye.elearning.data.model.KeycloakLogin
import nl.hearteye.elearning.data.model.User
import nl.hearteye.elearning.data.repository.ContentRepository
import nl.hearteye.elearning.data.repository.UserRepository
import nl.hearteye.elearning.data.store.DataStoreManager
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences,
    private val dataStoreManager: DataStoreManager,
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _loginResult = MutableStateFlow<Result<KeycloakLogin?>>(Result.success(null))
    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _profilePictureUpdateResult = mutableStateOf<Result<Unit>?>(null)
    val profilePictureUpdateResult: State<Result<Unit>?> = _profilePictureUpdateResult

    fun logout() {
        sharedPreferences.edit()
            .remove("auth_token")
            .remove("is_logged_in")
            .apply()

        viewModelScope.launch {
            dataStoreManager.setOnboardingCompleted(false)
        }

        _loginResult.value = Result.success(null)
    }

    fun saveLanguagePreference(language: String) {
        viewModelScope.launch {
            dataStoreManager.setSelectedLanguage(language)
        }
    }

    suspend fun getSelectedLanguage(): String? {
        return dataStoreManager.getSelectedLanguage()
    }

    fun mapLanguageCodeToDisplayName(languageCode: String): String {
        return when (languageCode) {
            "nl" -> "Nederlands"
            else -> "English"
        }
    }

    fun fetchCurrentUser() {
        viewModelScope.launch {
            try {
                val user = userRepository.getCurrentUser()

                // Only attempt to fetch the profile picture if profilePictureLocation is not null
                if (user.profilePictureLocation != null) {
                    try {
                        val content = contentRepository.getContent(user.profilePictureLocation)

                        // Set the user with the profile picture URL
                        _currentUser.value = user.copy(profilePicture = content.sasUrl)
                    } catch (e: Exception) {
                        _errorMessage.value = "Failed to fetch profile picture: ${e.message}"
                    }
                } else {
                    Log.d("d", "no profile picture")
                    _currentUser.value = user
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch current user: ${e.message}"
            }
        }
    }


    fun updateProfilePicture(id: String, profilePictureEntity: ProfilePictureEntity) {
        viewModelScope.launch {
            try {
                val response: Response<Unit> = userRepository.updateProfilePicture(id, profilePictureEntity)

                if (response.isSuccessful) {
                    _profilePictureUpdateResult.value = Result.success(Unit)
                } else {
                    _profilePictureUpdateResult.value = Result.failure(Exception("Profile picture update failed"))
                }
            } catch (e: Exception) {
                _profilePictureUpdateResult.value = Result.failure(Exception("Failed to update profile picture: ${e.message}"))
            }
        }
    }
}
