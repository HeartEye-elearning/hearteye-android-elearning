package nl.hearteye.elearning.data.repository

import nl.hearteye.elearning.data.api.DiscussionService
import nl.hearteye.elearning.data.mapper.DiscussionDetailMapper
import nl.hearteye.elearning.data.mapper.DiscussionMapper
import nl.hearteye.elearning.data.model.Discussion
import nl.hearteye.elearning.data.model.DiscussionDetail
import nl.hearteye.elearning.data.model.DiscussionResponse
import retrofit2.Response
import javax.inject.Inject

class DiscussionRepository @Inject constructor(
    private val discussionService: DiscussionService
) {
    suspend fun createDiscussion(discussion: Discussion) {
        val entity = DiscussionMapper.mapToEntity(discussion)
        discussionService.createDiscussion(entity)
    }

    suspend fun getDiscussions(page: Int, size: Int, creator: Boolean = false, search: String? = null): DiscussionResponse {
        val responseEntity = discussionService.getDiscussions(page, size, creator, search)
        return DiscussionMapper.mapToModel(responseEntity)
    }
    suspend fun getDiscussionById(id: String): DiscussionDetail {
        val discussionDetailEntity = discussionService.getDiscussionById(id)
        return DiscussionDetailMapper.map(discussionDetailEntity)
    }

    suspend fun deleteDiscussion(discussionId: String): Response<Unit> {
        return discussionService.deleteDiscussion(discussionId)
    }

}
