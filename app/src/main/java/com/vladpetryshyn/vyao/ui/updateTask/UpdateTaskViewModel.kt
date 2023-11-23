package com.vladpetryshyn.vyao.ui.updateTask

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladpetryshyn.vyao.repositories.room.tasks.Task
import com.vladpetryshyn.vyao.repositories.room.tasks.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class UpdateTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val notebookTitle: String = savedStateHandle.get<String>("notebookTitle")!!
    val taskId: String? = savedStateHandle.get<String>("taskId")

    // form fields
    var formTitle: MutableStateFlow<String> = MutableStateFlow("")
    var formDescription: MutableStateFlow<String> = MutableStateFlow("")

    init {
        if (taskId != null) {
            viewModelScope.launch(Dispatchers.Default) {
                val taskData = taskRepository.getTask(taskId.toInt())

                formTitle.update { taskData.title }
                formDescription.update { taskData.description }
            }
        }
    }

    fun updateTitle(title: String) {
        formTitle.update{title}
    }
    fun updateDescription(description: String) {
        formDescription.update{description}
    }

    fun createTask() {
        viewModelScope.launch(Dispatchers.Default) {
            if (taskId != null) {
                taskRepository.updateTask(
                    Task(
                        title = formTitle.value,
                        description = formDescription.value,
                        updated = Date(),
                        notebookTitle = notebookTitle,
                        id = taskId.toInt(),
                        timeSpent = 0,
                        scheduledFor = null
                    )
                )
            } else {
                taskRepository.createTask(
                    Task(
                        title = formTitle.value,
                        description = formDescription.value,
                        notebookTitle = notebookTitle,
                        updated = Date(),
                        scheduledFor = null,
                        timeSpent = 0
                    )
                )
            }
        }
    }
}
