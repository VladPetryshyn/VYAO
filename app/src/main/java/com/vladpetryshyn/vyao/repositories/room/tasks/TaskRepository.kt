package com.vladpetryshyn.vyao.repositories.room.tasks

import android.util.Log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun getTasksByNotebookTitle(notebookTitle: String): Flow<List<TaskWithEvents>> {
        return taskDao.getTasksByNotebookTitle(notebookTitle)
    }

    fun createTask(task : Task) {
        taskDao.createTask(task)
    }

    suspend fun getTask(taskId: Int): Task {
        return taskDao.getTask(taskId)
    }

    fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun searchTasks(searchQuery: String): List<Task> {
        return taskDao.searchTasks(
                "%$searchQuery%"
            )
    }

    suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    suspend fun markTaskAsDone(taskId: Int) {
        taskDao.markTaskAsDone(taskId)
    }

    suspend fun moveToOtherNotebook(newNotebook: String, taskId: Int) {
        taskDao.moveToOtherNotebook(newNotebook, taskId)
    }
}