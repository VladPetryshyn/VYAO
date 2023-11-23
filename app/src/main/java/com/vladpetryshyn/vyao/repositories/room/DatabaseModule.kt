package com.vladpetryshyn.vyao.repositories.room

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.vladpetryshyn.vyao.repositories.room.events.EventDao
import com.vladpetryshyn.vyao.repositories.room.notebooks.NotebookDao
import com.vladpetryshyn.vyao.repositories.room.tasks.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.datastore.preferences.core.Preferences

private const val VYAO_PREFERENCES = "vyao_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = VYAO_PREFERENCES,
)

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.taskDao()
    }

    @Singleton
    @Provides
    fun provideEventDao(appDatabase: AppDatabase): EventDao {
        return appDatabase.eventDao()
    }

    @Singleton
    @Provides
    fun provideNotebookDao(appDatabase: AppDatabase): NotebookDao {
        return appDatabase.notebookDao()
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return appContext.dataStore
    }
}