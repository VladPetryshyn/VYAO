package com.vladpetryshyn.vyao.repositories.room.notebooks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotebookDao {
    @Query("SELECT * FROM notebooks")
    fun getNotebooks(): Flow<List<Notebook>>

    @Query("SELECT title FROM notebooks")
    fun getNotebooksTitles(): Flow<List<String>>

    @Insert
    fun insertNotebook(notebook: Notebook)

    @Query("DELETE FROM notebooks WHERE title = :title")
    suspend fun deleteNotebook(title: String)

    @Query("UPDATE notebooks SET is_todo_notebook = 0 WHERE is_todo_notebook = 1")
    fun deleteTodo()

    @Query("UPDATE notebooks SET is_todo_notebook = 1 WHERE title = :title")
    fun makeTodo(title: String)

    @Query("SELECT * FROM notebooks WHERE is_todo_notebook = 1")
     fun getTodoNotebook(): Flow<Notebook>

    @Query("SELECT * FROM notebooks WHERE title LIKE :searchQuery")
    suspend fun searchNotebooks(searchQuery: String): List<Notebook>
}
