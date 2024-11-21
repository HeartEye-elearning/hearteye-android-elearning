package nl.hearteye.elearning.data.model

import nl.hearteye.elearning.data.entity.InformationPage

data class CourseDetail(
    val id: String,
    val title: String,
    val informationPages: List<InformationPage>,
    val questionCount: Int,
)