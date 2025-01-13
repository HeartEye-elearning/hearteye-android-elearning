package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.Base64ContentEntity
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
            category = entity.category,
            createdAt = entity.createdAt,
            fileLocation = entity.fileLocation,
            numOfComments = entity.numOfComments,
            imageLocation = entity.imageLocation
        )
    }

    fun mapToEntity(model: Discussion): DiscussionEntity {
        return DiscussionEntity(
            title = model.title,
            content = model.content,
            base64 = Base64ContentEntity(
                base64 = model.base64Content.base64,
                contentType = model.base64Content.contentType,
                title = model.base64Content.title
            ),
            category = model.category,
            profileImage = model.profileImage
        )
    }
}
