package nl.hearteye.elearning.data

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeneralRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KeycloakRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthenticatedClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthClient