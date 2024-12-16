package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.DiscussionContentEntity
import nl.hearteye.elearning.data.entity.DiscussionEntity
import nl.hearteye.elearning.data.entity.DiscussionResponseEntity
import nl.hearteye.elearning.data.model.Discussion
import nl.hearteye.elearning.data.model.DiscussionContent
import nl.hearteye.elearning.data.model.DiscussionResponse

object DiscussionMapper {

    fun mapToModel(entity: DiscussionResponseEntity): DiscussionResponse {
        return DiscussionResponse(
            totalPages = entity.totalPages,
            totalElements = entity.totalElements,
            numberOfElements = entity.numberOfElements,
            size = entity.size,
            content = entity.content.map { mapContentToModel(it) },
            pageable = entity.pageable,
            first = entity.first,
            last = entity.last,
            empty = entity.empty
        )
    }

    private fun mapContentToModel(entity: DiscussionContentEntity): DiscussionContent {
        return DiscussionContent(
            id = entity.id,
            userId = entity.userId,
            title = entity.title,
            content = entity.content,
            createdAt = entity.createdAt,
            fileLocation = entity.fileLocation,
            numOfComments = entity.numOfComments,
            base64 = entity.base64
        )
    }

    fun mapToEntity(model: Discussion): DiscussionEntity {
        return DiscussionEntity(
            title = model.title,
            content = model.content,
            category = model.category,
        )
    }
}
