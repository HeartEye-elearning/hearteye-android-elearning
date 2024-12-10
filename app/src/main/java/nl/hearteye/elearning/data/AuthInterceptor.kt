package nl.hearteye.elearning.data

import okhttp3.Interceptor
import okhttp3.Response
import android.content.SharedPreferences
import android.util.Log

class AuthInterceptor(private val sharedPreferences: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPreferences.getString("auth_token", null)
        if (token == null) {
            Log.e("AuthInterceptor", "No auth token found")
        }

        val request = chain.request().newBuilder().apply {
            token?.let {
                header("Authorization", "Bearer $it")
            }
        }.build()

        return chain.proceed(request)
    }
}

