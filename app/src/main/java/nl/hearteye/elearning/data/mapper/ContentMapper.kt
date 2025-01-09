package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.ContentEntity
import nl.hearteye.elearning.data.model.Content

object ContentMapper {
    fun map(entity: ContentEntity): Content {
        return Content(
            title = entity.title,
            contentType = entity.contentType,
            location = entity.location,
            sasUrl = entity.sasUrl
        )
    }
}
