package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.KeycloakLoginEntity
import nl.hearteye.elearning.data.model.KeycloakLogin

object KeycloakLoginMapper {
    fun map(entity: KeycloakLoginEntity): KeycloakLogin {
        return KeycloakLogin(
            accessToken = entity.accessToken,
            refreshToken = entity.refreshToken,
            expiresIn = entity.expiresIn,
            refreshExpiresIn = entity.refreshExpiresIn,
            tokenType = entity.tokenType
        )
    }
}
