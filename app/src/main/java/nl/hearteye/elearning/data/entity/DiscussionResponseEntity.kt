package nl.hearteye.elearning.data.entity

import com.squareup.moshi.Json

data class DiscussionResponseEntity(
    @Json(name = "totalPages") val totalPages: Int,
    @Json(name = "totalElements") val totalElements: Int,
    @Json(name = "numberOfElements") val numberOfElements: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "content") val content: List<DiscussionContentEntity>,
    @Json(name = "pageable") val pageable: Pageable,
    @Json(name = "first") val first: Boolean,
    @Json(name = "last") val last: Boolean,
    @Json(name = "empty") val empty: Boolean
)

data class Pageable(
    @Json(name = "unpaged") val unpaged: Boolean,
    @Json(name = "paged") val paged: Boolean,
    @Json(name = "pageNumber") val pageNumber: Int,
    @Json(name = "pageSize") val pageSize: Int,
    @Json(name = "offset") val offset: Int,
    @Json(name = "sort") val sort: List<Sort>
)

data class Sort(
    @Json(name = "direction") val direction: String,
    @Json(name = "nullHandling") val nullHandling: String,
    @Json(name = "ascending") val ascending: Boolean,
    @Json(name = "property") val property: String,
    @Json(name = "ignoreCase") val ignoreCase: Boolean
)

data class DiscussionContentEntity(
    @Json(name = "id") val id: String,
    @Json(name = "userId") val userId: String,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "category") val category: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "fileLocation") val fileLocation: String?,
    @Json(name = "numOfComments") val numOfComments: Int
)

