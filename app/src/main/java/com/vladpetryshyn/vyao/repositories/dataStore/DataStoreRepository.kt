package com.vladpetryshyn.vyao.repositories.dataStore

import android.net.Uri
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
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
}
