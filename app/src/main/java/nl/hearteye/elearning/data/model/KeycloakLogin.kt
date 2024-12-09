package nl.hearteye.elearning.data.model

data class KeycloakLogin(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val refreshExpiresIn: Int,
    val tokenType: String
)