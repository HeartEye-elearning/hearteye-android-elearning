package nl.hearteye.elearning.data.api

import nl.hearteye.elearning.data.entity.DiscussionEntity
import nl.hearteye.elearning.data.entity.DiscussionResponseEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DiscussionService {

    @POST("forum-service/v1/forum")
    suspend fun createDiscussion(@Body discussion: DiscussionEntity)

    @GET("forum-service/v1/forum")
    suspend fun getDiscussions(): List<DiscussionResponseEntity>
}
