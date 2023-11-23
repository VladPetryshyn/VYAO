package com.vladpetryshyn.vyao.ui.addEvent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import com.vladpetryshyn.vyao.repositories.room.events.Event
import com.vladpetryshyn.vyao.repositories.room.events.EventRepository
import com.vladpetryshyn.vyao.repositories.room.events.generateEventWorkerName
import com.vladpetryshyn.vyao.repositories.room.tasks.Task
import com.vladpetryshyn.vyao.repositories.room.tasks.TaskRepository
import com.vladpetryshyn.vyao.repositories.workmanager.WorkManagerNotificationRepository
import com.vladpetryshyn.vyao.workers.TASK_DESCRIPTION
import com.vladpetryshyn.vyao.workers.TASK_ID
import com.vladpetryshyn.vyao.workers.TASK_TITLE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

val dateFormater = DateTimeFormatter.ofPattern("dd-MM-yyyy")
fun formatDate(newDate: Long): String  {
    return LocalDate
        .ofEpochDay(newDate)
        .format(dateFormater)
}

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle,
    private val workManagerNotificationRepository: WorkManagerNotificationRepository,
    private val eventRepository: EventRepository,
) : ViewModel() {
    private val paramsDate = savedStateHandle.get<String>("date")
    private val taskId = savedStateHandle.get<String>("taskId")

    private val currentTime = LocalDateTime.now()

    // date
    var selectedDate by mutableStateOf<Long>(
        paramsDate?.toLong() ?:
        currentTime.toLocalDate().toEpochDay()
    )
    private set

    var selectDateFormatted by mutableStateOf<String>(formatDate(selectedDate))
    private set

    // time
    var selectedHour by mutableStateOf<Int>(currentTime.hour)
    private set
    var selectedMinutes by mutableStateOf<Int>(currentTime.minute)
    private set


    // task
    var taskItems: MutableStateFlow<List<Task>>
            = MutableStateFlow<List<Task>>(listOf())
        private set
    var searchQuery by mutableStateOf("")
        private set

    var selectedTask: Task? by mutableStateOf(null)

    init {
        viewModelScope.launch {
            if (taskId != null) {
                selectedTask = taskRepository.getTask(taskId.toInt())
            }
        }
    }


    fun updateSelectedDate(newDate: Long) {
        selectedDate = newDate
        selectDateFormatted = formatDate(newDate)
    }
    fun updateSelectedTime(newHours: Int, newMinutes: Int) {
        selectedHour = newHours
        selectedMinutes = newMinutes
    }
    fun updateSelectedTask(task: Task) {
        selectedTask = task
    }

    fun updateSearchQuery(newSearchQuery: String) {
        searchQuery = newSearchQuery

        viewModelScope.launch(Dispatchers.Default) {
            taskItems.update {
                taskRepository.searchTasks(searchQuery)
            }
        }
    }

    fun createReminder(
        goBack: () -> Unit,
        displayToast: () -> Unit,
    ) {
        val reminderDate = LocalDate.ofEpochDay(selectedDate)

        val customCalendar = Calendar.getInstance()
        // the month is +1
        customCalendar.set(
            reminderDate.year,
            reminderDate.month.value -1,
            reminderDate.dayOfMonth,
            selectedHour,
            selectedMinutes,
            0,
        )

        val customTime = customCalendar.timeInMillis
        val calendar = Calendar.getInstance()
        val currentTime = calendar.timeInMillis

        if (selectedTask != null) {
            val delay = customTime - currentTime
            val workName = generateEventWorkerName(delay, selectedTask?.id ?: 1)


            viewModelScope.launch(Dispatchers.Default) {
                val data = Data.Builder()
                data.putString(TASK_TITLE, selectedTask?.title)
                data.putString(TASK_DESCRIPTION, selectedTask?.description)
                data.putInt(TASK_ID, selectedTask?.id ?: 1)

                eventRepository.insertEvent(
                    Event(
                        taskId = selectedTask!!.id,
                        dateKey = reminderDate.toString(),
                        reminderDate = customCalendar.time,
                        workerId = workName,
                        yearMonth = YearMonth.of(reminderDate.year, reminderDate.month).toString()
                    ),
                    data.build(),
                    delay,
                    workName
                )
            }

            goBack()
        } else {
            displayToast()
        }
    }
}