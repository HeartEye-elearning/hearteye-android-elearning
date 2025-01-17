package nl.hearteye.elearning.ui.screens.login

import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.hearteye.elearning.BuildConfig
import nl.hearteye.elearning.data.model.KeycloakLogin
import nl.hearteye.elearning.data.repository.KeycloakLoginRepository
import nl.hearteye.elearning.data.store.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val keycloakLoginRepository: KeycloakLoginRepository,
    private val sharedPreferences: SharedPreferences,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val clientSecret = BuildConfig.CLIENT_SECRET

    private val _loginResult = MutableStateFlow<Result<KeycloakLogin?>>(Result.success(null))
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = keycloakLoginRepository.login(username, password, clientSecret)
                _loginResult.value = Result.success(response)
                saveUserLoginState(response)

            } catch (exception: Exception) {
                _loginResult.value = Result.failure(exception)
                _errorMessage.value = exception.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun saveUserLoginState(loginResponse: KeycloakLogin?) {
        loginResponse?.let {
            val expirationTime = System.currentTimeMillis() + (it.expiresIn * 1000L)
            val expirationTimeRefresh = System.currentTimeMillis() + (it.refreshExpiresIn * 1000L)
            sharedPreferences.edit()
                .putString("auth_token", it.accessToken)
                .putString("refresh_token", it.refreshToken)
                .putBoolean("is_logged_in", true)
                .putLong("token_expiration", expirationTime)
                .putLong("refresh_token_expiration", expirationTimeRefresh)
                .apply()
        }
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }


    private val _isOnboardingCompleted = MutableStateFlow(false)
    val isOnboardingCompleted: StateFlow<Boolean> get() = _isOnboardingCompleted

    init {
        checkOnboardingStatus()
    }

    private fun checkOnboardingStatus() {
        viewModelScope.launch {
            _isOnboardingCompleted.value = dataStoreManager.isOnboardingCompleted()
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            dataStoreManager.setOnboardingCompleted(true)
            _isOnboardingCompleted.value = true
        }
    }

    fun saveLanguagePreference(language: String) {
        viewModelScope.launch {
            dataStoreManager.setSelectedLanguage(language)
        }
    }
}







