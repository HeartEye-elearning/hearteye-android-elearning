package nl.hearteye.elearning.data.store

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreManager( @ApplicationContext private val context: Context) {

    private val onboardingKey = booleanPreferencesKey("onboarding_completed")
    private val languageKey = stringPreferencesKey("selected_language")

    suspend fun isOnboardingCompleted(): Boolean {
        return context.dataStore.data
            .map { preferences -> preferences[onboardingKey] == true }
            .first()
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[onboardingKey] = completed
        }
    }

    suspend fun getSelectedLanguage(): String? {
        return context.dataStore.data
            .map { preferences -> preferences[languageKey] }
            .first()
    }

    suspend fun setSelectedLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[languageKey] = language
        }
    }
}