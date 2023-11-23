package com.vladpetryshyn.vyao.ui.tasks

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.Data
import com.vladpetryshyn.vyao.VyaoApplication
import com.vladpetryshyn.vyao.repositories.room.events.Event
import com.vladpetryshyn.vyao.repositories.room.events.EventRepository
import com.vladpetryshyn.vyao.repositories.room.notebooks.Notebook
import com.vladpetryshyn.vyao.repositories.room.notebooks.NotebooksRoomRepository
import com.vladpetryshyn.vyao.repositories.room.tasks.Task
import com.vladpetryshyn.vyao.repositories.room.tasks.TaskRepository
import com.vladpetryshyn.vyao.repositories.room.tasks.TaskWithEvents
import com.vladpetryshyn.vyao.workers.TASK_DESCRIPTION
import com.vladpetryshyn.vyao.workers.TASK_ID
import com.vladpetryshyn.vyao.workers.TASK_TITLE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val savedStateHandle: SavedStateHandle,
    private val eventRepository: EventRepository,
    private val notebooksRoomRepository: NotebooksRoomRepository,
) : ViewModel() {
    val notebookTitle: String = savedStateHandle.get<String>("notebookTitle")!!

    val tasks: StateFlow<List<TaskWithEvents>> = taskRepository.getTasksByNotebookTitle(notebookTitle)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )

    // notebooks
    var notebooksSearchItems: MutableStateFlow<List<Notebook>>
            = MutableStateFlow(listOf())
        private set

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            taskRepository.deleteTask(taskId)
        }
    }

    fun markTaskAsDone(taskId: Int) {
        viewModelScope.launch {
            taskRepository.markTaskAsDone(taskId)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.deleteEvent(event)
        }
    }

    fun updateEvent(
        event: Event,
        task: Task,
        calendar: Calendar
    ) {
            val data = Data.Builder()
            data.putString(TASK_TITLE, task.title)
            data.putString(TASK_DESCRIPTION, task.description)
            data.putInt(TASK_ID, task.id ?: 1)

            val delay = calendar.timeInMillis - Calendar.getInstance().timeInMillis
            val reminderDate = LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault())

            viewModelScope.launch {
                eventRepository.updateEvent(
                    Event(
                        taskId = event.taskId,
                        yearMonth = YearMonth.of(reminderDate.year, reminderDate.month).toString(),
                        dateKey = reminderDate.toString(),
                        reminderDate = calendar.time,
                        workerId = event.workerId
                    ),
                    data.build(),
                    delay
                )
            }
        }

    fun moveTask(notebookTitle: String, taskId: Int) {
        viewModelScope.launch {
            taskRepository.moveToOtherNotebook(notebookTitle, taskId)
        }
    }

    fun searchForNotebooks(searchQuery: String) {
        viewModelScope.launch {
            notebooksSearchItems.update {
                notebooksRoomRepository.searchNotebooks(searchQuery)
            }
        }
    }
}

fun CreationExtras.vyaoApplication(): VyaoApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VyaoApplication)
