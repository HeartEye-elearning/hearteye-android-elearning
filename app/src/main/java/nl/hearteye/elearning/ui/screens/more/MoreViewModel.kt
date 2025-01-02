package nl.hearteye.elearning.ui.screens.more

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.model.KeycloakLogin
import nl.hearteye.elearning.data.model.User
import nl.hearteye.elearning.data.repository.UserRepository
import nl.hearteye.elearning.data.store.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _loginResult = MutableStateFlow<Result<KeycloakLogin?>>(Result.success(null))
    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

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
                _currentUser.value = user
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch current user: ${e.message}"
            }
        }
    }
}

