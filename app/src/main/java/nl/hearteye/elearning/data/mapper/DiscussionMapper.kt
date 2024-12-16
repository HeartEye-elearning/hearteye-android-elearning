package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.DiscussionContentEntity
import nl.hearteye.elearning.data.entity.DiscussionEntity
import nl.hearteye.elearning.data.entity.DiscussionResponseEntity
import nl.hearteye.elearning.data.model.Discussion
import nl.hearteye.elearning.data.model.DiscussionContent
import nl.hearteye.elearning.data.model.DiscussionResponse

object DiscussionMapper {

    // Map the entire response entity to a model response
    fun mapToModel(entity: DiscussionResponseEntity): DiscussionResponse {
        return DiscussionResponse(
            totalPages = entity.totalPages,
            totalElements = entity.totalElements,
            numberOfElements = entity.numberOfElements,
            size = entity.size,
            content = entity.content.map { mapContentToModel(it) }, // Mapping each content
            pageable = entity.pageable,
            first = entity.first,
            last = entity.last,
            empty = entity.empty
        )
    }

    // Map the content of the discussion entity to the model
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
        )
    }

    // Convert Discussion model to entity for saving in DB or posting to API
    fun mapToEntity(model: Discussion): DiscussionEntity {
        return DiscussionEntity(
            title = model.title,
            content = model.content,
            category = model.category,
        )
    }
}
