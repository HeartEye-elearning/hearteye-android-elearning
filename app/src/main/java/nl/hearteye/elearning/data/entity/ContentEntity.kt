package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json

data class ContentEntity(
    @Json(name = "title") val title: String,
    @Json(name = "contentType") val contentType: String,
    @Json(name = "location") val location: String,
    @Json(name = "sasUrl") val sasUrl: String
)