package nl.hearteye.elearning.data.repository

import nl.hearteye.elearning.data.api.ContentService
import nl.hearteye.elearning.data.entity.ContentEntity
import javax.inject.Inject

class ContentRepository @Inject constructor(
    private val contentService: ContentService
) {
    suspend fun getContent(blobIdentifier: String): ContentEntity {
        return contentService.getContent(blobIdentifier)
    }
}
