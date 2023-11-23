package com.vladpetryshyn.vyao.repositories.room.tasks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Transaction
    @Query("SELECT * FROM task WHERE notebook_title = :notebookTitle")
    fun getTasksByNotebookTitle(notebookTitle: String): Flow<List<TaskWithEvents>>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTask(id: Int): Task

    @Insert
    fun createTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("SELECT * FROM task WHERE title LIKE :searchQuery ORDER BY modification_date DESC")
    suspend fun searchTasks(searchQuery: String): List<Task>

    @Query("DELETE FROM task WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("UPDATE task SET is_done = CASE WHEN is_done = 0 then 1 else 0 end WHERE id = :taskId")
    suspend fun markTaskAsDone(taskId: Int)

    @Query("UPDATE task SET notebook_title = :newNotebook WHERE id = :taskId")
    suspend fun moveToOtherNotebook(newNotebook: String, taskId: Int)

//    @Query("DELETE FROM task WHERE notebookTitle = :title")
//    suspend fun deleteByNotebookTitle(title: String)
}