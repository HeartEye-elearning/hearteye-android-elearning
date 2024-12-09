package nl.hearteye.elearning.data.repository

import nl.hearteye.elearning.data.api.KeycloakService
import nl.hearteye.elearning.data.mapper.KeycloakLoginMapper
import nl.hearteye.elearning.data.model.KeycloakLogin
import javax.inject.Inject

class KeycloakLoginRepository @Inject constructor(
    private val keycloakService: KeycloakService
) {
    suspend fun login(username: String, password: String, clientSecret: String): KeycloakLogin {
        val entity = keycloakService.login(username, password, clientSecret = clientSecret)
        return KeycloakLoginMapper.map(entity)
    }

    suspend fun refreshToken(refreshToken: String): KeycloakLogin {
        val entity = keycloakService.refreshToken(refreshToken)
        return KeycloakLoginMapper.map(entity)
    }
}

