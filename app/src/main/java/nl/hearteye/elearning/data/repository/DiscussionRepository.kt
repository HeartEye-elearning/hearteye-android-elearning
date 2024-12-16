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

    suspend fun getDiscussions(): List<DiscussionResponse> {
        val responseEntities = discussionService.getDiscussions()
        return responseEntities.map { DiscussionMapper.mapToModel(it) }
    }
}
