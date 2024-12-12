package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.DiscussionEntity
import nl.hearteye.elearning.data.model.Discussion

object DiscussionMapper {
    fun map(entity: DiscussionEntity): Discussion {
        return Discussion(
            title = entity.title,
            content = entity.content,
            base64 = entity.base64
        )
    }
}
