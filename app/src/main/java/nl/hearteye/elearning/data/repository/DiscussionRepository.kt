package nl.hearteye.elearning.data.repository

import nl.hearteye.elearning.data.api.DiscussionService
import nl.hearteye.elearning.data.mapper.DiscussionMapper
import nl.hearteye.elearning.data.model.Discussion
import nl.hearteye.elearning.data.model.DiscussionResponse
import javax.inject.Inject

class DiscussionRepository @Inject constructor(
    private val discussionService: DiscussionService
) {
    suspend fun createDiscussion(discussion: Discussion) {
        val entity = DiscussionMapper.mapToEntity(discussion)
        discussionService.createDiscussion(entity)
    }

    suspend fun getDiscussions(page: Int = 0, size: Int = 10, creator: Boolean = false): DiscussionResponse {
        val responseEntity = discussionService.getDiscussions(page, size, creator)
        return DiscussionMapper.mapToModel(responseEntity)
    }

}
