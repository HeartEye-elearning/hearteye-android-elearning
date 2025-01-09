package nl.hearteye.elearning.data.api

import nl.hearteye.elearning.data.entity.ContentEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface ContentService {
    @GET("content-service/v1/Content/blob/{blobIdentifier}")
    suspend fun getContent(@Path("blobIdentifier") blobIdentifier: String): ContentEntity
}
