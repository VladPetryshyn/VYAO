package com.vladpetryshyn.vyao.ui.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.vladpetryshyn.vyao.repositories.room.events.Event
import com.vladpetryshyn.vyao.repositories.room.events.EventRepository
import com.vladpetryshyn.vyao.repositories.room.tasks.Task
import com.vladpetryshyn.vyao.workers.TASK_DESCRIPTION
import com.vladpetryshyn.vyao.workers.TASK_ID
import com.vladpetryshyn.vyao.workers.TASK_TITLE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    var selectedDay by mutableStateOf(
        CalendarDay(date = LocalDate.now(), position = DayPosition.MonthDate)
    )
    private set

    var selectedYearMonth: YearMonth by mutableStateOf(
        YearMonth.now()
    )

    var events = eventRepository.getByDate(selectedDay.date.toString())
    var monthEvents = eventRepository.selectByYearMonth(selectedYearMonth.toString())

    fun updateMonthEvents(yearMonth: YearMonth) {
        selectedYearMonth = yearMonth
        monthEvents = eventRepository.selectByYearMonth(yearMonth = yearMonth.toString())
    }

    fun updateSelectedDay(day: CalendarDay) {
        if (day != selectedDay) {
            selectedDay = day
            events = eventRepository.getByDate(day.date.toString())
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
        data.putInt(TASK_ID, task.id)

        val delay = calendar.timeInMillis - Calendar.getInstance().timeInMillis
        val reminderDate = LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault())

        viewModelScope.launch {
            eventRepository.updateEvent(
                Event(
                    taskId = event.taskId,
                    yearMonth = YearMonth.of(reminderDate.year, reminderDate.month).toString(),
                    dateKey = reminderDate.toString(),
                    workerId = event.workerId,
                    reminderDate = calendar.time
                ),
                data.build(),
                delay,
            )
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.deleteEvent(event)
        }
    }
}