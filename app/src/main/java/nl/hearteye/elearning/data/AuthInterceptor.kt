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

        val response = chain.proceed(request)

        if (response.code == 401) {
            clearUserSession()
        }

        return response
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