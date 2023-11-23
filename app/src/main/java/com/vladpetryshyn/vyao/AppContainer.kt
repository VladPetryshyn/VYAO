package com.vladpetryshyn.vyao

import android.content.Context
import com.vladpetryshyn.vyao.repositories.room.AppDatabase
import com.vladpetryshyn.vyao.repositories.room.notebooks.NotebooksRoomRepository
import dagger.hilt.android.HiltAndroidApp

interface AppContainer {
    val notebooksRepository: NotebooksRoomRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val notebooksRepository: NotebooksRoomRepository by lazy {
        NotebooksRoomRepository(AppDatabase.getDatabase(context).notebookDao())
    }
}