package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json

data class ProfilePictureEntity(
    @Json(name = "base64Image") val base64Image: String,
    @Json(name = "contentType") val contentType: String
)
