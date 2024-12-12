package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json

data class UserEntity(
    val id: String,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "email") val email: String,
    @Json(name = "role") val role: String
)