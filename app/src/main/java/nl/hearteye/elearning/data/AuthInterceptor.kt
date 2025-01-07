package nl.hearteye.elearning.data

import okhttp3.Interceptor
import okhttp3.Response
import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var token = sharedPreferences.getString("auth_token", null)
        val refreshToken = sharedPreferences.getString("refresh_token", null)


        val tokenExpirationTime = sharedPreferences.getLong("token_expiration", 0L)
        val refreshTokenExpirationTime = sharedPreferences.getLong("refresh_token_expiration", 0L)

        if (System.currentTimeMillis() > tokenExpirationTime) {
            if (System.currentTimeMillis() > refreshTokenExpirationTime) {
                Log.e("AuthInterceptor", "Both auth token and refresh token are expired")
                clearUserSession()
            } else {
                token = refreshToken
            }
        }

        val request = chain.request().newBuilder().apply {
            token?.let {
                header("Authorization", "Bearer $it")
            }
        }.build()
        // Check if the request has a body and log it
        val body = request.body
        if (body != null) {
            val buffer = okio.Buffer()
            body.writeTo(buffer)
            val bodyString = buffer.readUtf8()
            Log.d("AuthInterceptor", "Request Body: $bodyString")  // Log the request body
        }
            return chain.proceed(request)
    }

    private fun clearUserSession() {
        sharedPreferences.edit()
            .remove("auth_token")
            .remove("refresh_token")
            .remove("is_logged_in")
            .remove("token_expiration")
            .remove("refresh_token_expiration")
            .apply()
    }
}


