package com.vladpetryshyn.vyao.repositories.dataStore

import android.net.Uri
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vladpetryshyn.vyao.ui.settings.LookAndFeelViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val TODO_NOTEBOOK_PREFERENCE = stringPreferencesKey("todo_notebook_preference")
        val APP_AVATAR_URL = stringPreferencesKey("app_avatar_url")
        val IS_JUST_BLACK =  booleanPreferencesKey("theme_is_just_black")
        val USE_MATERIAL_COLORS = booleanPreferencesKey("use_material_colors")
        val THEME_COLOR_PREFERENCES = stringPreferencesKey("theme_color_preferences")
        val TAG = "DATA_STORE_REPOSITORY"
    }

    suspend fun saveTodoNotebook(notebook: String) {
        dataStore.edit { preferences ->
            preferences[TODO_NOTEBOOK_PREFERENCE] = notebook
        }
    }

    suspend fun saveUserAvatar(url: Uri) {
        dataStore.edit { preferences ->
            preferences[APP_AVATAR_URL] = url.toString()
        }
    }

    val userAvatar: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading avatar preferences.", it)
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[APP_AVATAR_URL] ?: ""
        }

    val todoNotebook: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading todo preferences.", it)
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[TODO_NOTEBOOK_PREFERENCE] ?: ""
        }

    val isJustBlackSelected: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading just black preference", it)
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_JUST_BLACK] ?: false
        }

    suspend fun setIsJustBlack(isJustBlack: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_JUST_BLACK] = isJustBlack
        }
    }

    val themeColor: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading todo preferences.", it)
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[THEME_COLOR_PREFERENCES] ?: LookAndFeelViewModel.selectedColor
        }

    suspend fun setThemeColor(themeColor: String) {
        dataStore.edit { preferences ->
            preferences[THEME_COLOR_PREFERENCES] = themeColor
        }
    }

    val useMaterialColors: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading todo preferences.", it)
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[USE_MATERIAL_COLORS] ?: true
        }

    suspend fun setUseMaterialColors(useMaterialColors: Boolean) {
        dataStore.edit { preferences ->
            preferences[USE_MATERIAL_COLORS] = useMaterialColors
        }
    }
}
