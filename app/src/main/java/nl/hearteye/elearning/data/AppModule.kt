package nl.hearteye.elearning.data

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.hearteye.elearning.data.api.CourseService
import nl.hearteye.elearning.data.api.KeycloakService
import nl.hearteye.elearning.data.api.UserService
import nl.hearteye.elearning.data.store.DataStoreManager
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    @GeneralRetrofit
    fun provideRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl("https://hearteye-bravo-dev.apps.inholland.hcs-lab.nl/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    @KeycloakRetrofit
    fun provideKeycloakRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl("https://keycloak-bravo-dev.apps.inholland.hcs-lab.nl/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideCourseService(@GeneralRetrofit retrofit: Retrofit): CourseService =
        retrofit.create(CourseService::class.java)

    @Provides
    @Singleton
    fun provideUserService(@GeneralRetrofit retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideKeycloakService(@KeycloakRetrofit retrofit: Retrofit): KeycloakService =
        retrofit.create(KeycloakService::class.java)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}
