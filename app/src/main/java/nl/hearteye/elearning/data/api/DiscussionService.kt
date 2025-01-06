package nl.hearteye.elearning.data.api

import nl.hearteye.elearning.data.entity.CommentEntity
import nl.hearteye.elearning.data.entity.DiscussionDetailEntity
import nl.hearteye.elearning.data.entity.DiscussionEntity
import nl.hearteye.elearning.data.entity.DiscussionResponseEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DiscussionService {

    @POST("forum-service/v1/forum")
    suspend fun createDiscussion(@Body discussion: DiscussionEntity)

    @GET("forum-service/v1/forum")
    suspend fun getDiscussions(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("creator") creator: Boolean? = null,
        @Query("search") search: String? = null,
        @Query("category") category: String? = null,
    ): DiscussionResponseEntity

    @GET("forum-service/v1/forum/{id}")
    suspend fun getDiscussionById(
        @Query("id") id: String,
    ): DiscussionDetailEntity

    @DELETE("forum-service/v1/forum/{id}")
    suspend fun deleteDiscussion(
        @Path("id") id: String
    ): Response<Unit>

    @POST("forum-service/v1/forum/{id}/comment")
    suspend fun createComment(
        @Path("id") discussionId: String,
        @Body comment: CommentEntity
    ): Response<Unit>
}
