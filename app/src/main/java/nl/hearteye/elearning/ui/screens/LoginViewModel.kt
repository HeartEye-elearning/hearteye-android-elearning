package nl.hearteye.elearning.ui.screens

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.hearteye.elearning.data.model.KeycloakLogin
import nl.hearteye.elearning.data.repository.KeycloakLoginRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val keycloakLoginRepository: KeycloakLoginRepository,
    public val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val clientSecret = "szTfCajigkrFeYMjZYPBf31KqlMnAT7h"

    private val _loginResult = MutableStateFlow<Result<KeycloakLogin?>>(Result.success(null))
    val loginResult: StateFlow<Result<KeycloakLogin?>> = _loginResult
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
                _errorMessage.value = "Failed to login: ${exception.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun saveUserLoginState(loginResponse: KeycloakLogin?) {
        loginResponse?.let {
            sharedPreferences.edit()
                .putString("auth_token", it.accessToken)
                .putBoolean("is_logged_in", true)
                .apply()
        }
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    fun logout() {
        sharedPreferences.edit()
            .remove("auth_token")
            .remove("is_logged_in")
            .apply()

        _loginResult.value = Result.success(null)
    }
}






