package nl.hearteye.elearning.data.api

import nl.hearteye.elearning.data.entity.KeycloakLoginEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface KeycloakService {
    @FormUrlEncoded
    @POST("realms/hearteye-elearning/protocol/openid-connect/token")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = "password",
        @Field("client_id") clientId: String = "android",
        @Field("client_secret") clientSecret: String
    ): KeycloakLoginEntity

    @FormUrlEncoded
    @POST("realms/hearteye-elearning/protocol/openid-connect/token")
    suspend fun refreshToken(
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("client_id") clientId: String = "android"
    ): KeycloakLoginEntity
}
