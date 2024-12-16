package nl.hearteye.elearning.data.model

import nl.hearteye.elearning.data.entity.Base64Content
import nl.hearteye.elearning.data.entity.Pageable

data class DiscussionResponse(
    val totalPages: Int,
    val totalElements: Int,
    val numberOfElements: Int,
    val size: Int,
    val content: List<DiscussionContent>,
    val pageable: Pageable,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
)

data class Pageable(
    val unpaged: Boolean,
    val paged: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val offset: Int,
    val sort: List<Sort>
)

data class Sort(
    val direction: String,
    val nullHandling: String,
    val ascending: Boolean,
    val property: String,
    val ignoreCase: Boolean
)

data class DiscussionContent(
    val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val fileLocation: String,
    val numOfComments: Int,
    val base64: Base64Content
)
