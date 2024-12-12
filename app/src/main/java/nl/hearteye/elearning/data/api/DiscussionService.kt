package nl.hearteye.elearning.data.api

import nl.hearteye.elearning.data.entity.DiscussionEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DiscussionService {

    @GET("forum-service/v1/forum")
    suspend fun getDiscussions(): List<DiscussionEntity>

    @POST("forum-service/v1/forum")
    suspend fun createDiscussion(@Body discussion: DiscussionEntity)
}