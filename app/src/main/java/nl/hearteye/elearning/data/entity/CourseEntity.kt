package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json

data class CourseEntity(
    val id: String,
    @Json(name = "title") val title: Map<String, String>,
    @Json(name = "description") val description: Map<String, String>,
    @Json(name = "durationInMinutes") val durationInMinutes: Int,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "imageLocation") val imageLocation: String
)