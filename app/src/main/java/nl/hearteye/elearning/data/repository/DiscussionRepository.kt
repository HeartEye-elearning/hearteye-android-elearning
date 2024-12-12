package nl.hearteye.elearning.data.repository

import nl.hearteye.elearning.data.api.DiscussionService
import nl.hearteye.elearning.data.entity.DiscussionEntity
import nl.hearteye.elearning.data.mapper.DiscussionMapper
import nl.hearteye.elearning.data.model.Discussion
import javax.inject.Inject

class DiscussionRepository @Inject constructor(
    private val discussionService: DiscussionService
) {

    suspend fun getDiscussions(): List<Discussion> {
        val apiDiscussions = discussionService.getDiscussions()
        return apiDiscussions.map { DiscussionMapper.map(it) }
    }

    suspend fun createDiscussion(discussion: Discussion) {
        val entity = DiscussionEntity(
            title = discussion.title,
            content = discussion.content,
            base64 = discussion.base64
        )
        discussionService.createDiscussion(entity)
    }
}
