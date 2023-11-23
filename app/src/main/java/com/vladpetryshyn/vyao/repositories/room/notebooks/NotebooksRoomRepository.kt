package com.vladpetryshyn.vyao.repositories.room.notebooks

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotebooksRoomRepository @Inject
    constructor(private val notebookDao: NotebookDao) {
     fun getNotebooks(): Flow<List<Notebook>> {
        return notebookDao.getNotebooks()
    }

     fun getNotebooksTitles(): Flow<List<String>> {
        return notebookDao.getNotebooksTitles()
    }

     fun insertNotebook(notebook: Notebook) {
        notebookDao.insertNotebook(notebook = notebook)
    }

     suspend fun deleteNotebook(title: String) {
        notebookDao.deleteNotebook(title)
    }

    fun makeTodo(title: String) {
        notebookDao.makeTodo(title)
    }
    fun deleteTodo() {
        notebookDao.deleteTodo()
    }

    fun getTodoNotebook(): Flow<Notebook> {
        return notebookDao.getTodoNotebook()
    }


    suspend fun searchNotebooks(searchQuery: String): List<Notebook> {
        return notebookDao.searchNotebooks("%$searchQuery%")
    }
}