package com.vladpetryshyn.vyao.ui.calendar
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.vladpetryshyn.vyao.BackPressHandler
import com.vladpetryshyn.vyao.R
import com.vladpetryshyn.vyao.repositories.room.events.Event
import com.vladpetryshyn.vyao.repositories.room.events.EventWithTask
import com.vladpetryshyn.vyao.repositories.room.tasks.Task
import com.vladpetryshyn.vyao.ui.EventDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

// works ok in release  version
@Composable
@ExperimentalMaterial3Api
fun CalendarScreen(
    navigateBack: () -> Unit,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    addEvent: (t: Long) -> Unit
) {
    val sheetState = rememberStandardBottomSheetState()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    var events: List<EventWithTask> by remember {
        mutableStateOf(listOf())
    }

    var monthEvents: List<Event> by remember {
        mutableStateOf(listOf())
    }

    LaunchedEffect(calendarViewModel.selectedDay) {
        calendarViewModel.events.collect { roomEvents ->
            events = roomEvents
        }
    }
    LaunchedEffect(calendarViewModel.selectedYearMonth) {
        calendarViewModel.monthEvents.collect {events ->
            monthEvents = events
        }
    }
    Log.d("DKDK", monthEvents.toString())

    val scope = rememberCoroutineScope()

    BackPressHandler {
        scope.launch {
            if (sheetState.currentValue == SheetValue.Expanded) {
                sheetState.show()
            } else {
                navigateBack()
            }
        }
    }

    val configuration = LocalConfiguration.current
    val localDensity = LocalDensity.current
    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }
    var columnHeightPx by remember {
        mutableFloatStateOf(0f)
    }

    val screenHeight = configuration.screenHeightDp.dp - columnHeightDp - 90.dp

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                addEvent(calendarViewModel.selectedDay.date.toEpochDay())
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        }
    ) {
        BottomSheetScaffold(
            sheetContent = {
                SheetComposable(
                    selectedDay = calendarViewModel.selectedDay.date,
                    tasks = events,
                    deleteEvent = calendarViewModel::deleteEvent,
                    updateEvent = calendarViewModel::updateEvent
                )
            },
            sheetPeekHeight = screenHeight,
            scaffoldState = scaffoldState,
            modifier = Modifier
                .padding(it)
        ) {
            CalendarComposable(
                selectedDay = calendarViewModel.selectedDay,
                updateSelectedDate = calendarViewModel::updateSelectedDay,
                modifier = Modifier
                    .onGloballyPositioned {coordinates ->
                        columnHeightPx = coordinates.size.height.toFloat()
                        columnHeightDp = with(localDensity) { coordinates.size.height.toDp() }
                    },
                updateYearMonth = calendarViewModel::updateMonthEvents,
                monthEvents = monthEvents
            )
        }
    }
}


@Composable
fun SheetComposable(
    selectedDay: LocalDate,
    tasks: List<EventWithTask>,
    deleteEvent: (event: Event) -> Unit,
    updateEvent: (event: Event, task: Task, calendar: Calendar) -> Unit
) {
    if (tasks.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(id = R.string.calendar_sheet_empty_day))
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Column {
                    Text(
                        text = selectedDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    LazyColumn {
                        items(tasks) { eventWithTask ->
                            var isEventDialogShown by remember {
                                mutableStateOf(false)
                            }

                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                            )
                            Row(
                                modifier = Modifier.height(IntrinsicSize.Max)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val formattedTime = LocalDateTime.ofInstant(
                                        eventWithTask.event.reminderDate.toInstant(),
                                        ZoneOffset.systemDefault(),
                                    ).format(DateTimeFormatter.ofPattern("HH:mm"))

                                    Text(
                                        text = formattedTime,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                // filling max height
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(vertical = 15.dp)
                                        .weight(1f)
                                ) {
                                    val textDecoration = if (eventWithTask.task.isDone) {
                                        TextDecoration.LineThrough
                                    }  else {
                                        null
                                    }
                                    Text(
                                        text = eventWithTask.task.title,
                                        style = MaterialTheme.typography.bodyLarge,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textDecoration = textDecoration
                                    )
                                    Text(
                                        text = eventWithTask.task.description,
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 2,
                                        modifier = Modifier.padding(top = 5.dp),
                                        overflow = TextOverflow.Ellipsis,
                                        textDecoration = textDecoration
                                    )
                                }
                                IconButton(
                                    onClick = { isEventDialogShown = true },
                                    modifier = Modifier.padding(
                                        top = 15.dp,
                                        start = 5.dp
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = ""
                                    )
                                }
                            }

                            if (isEventDialogShown) {
                                EventDialog(
                                    date = eventWithTask.event.reminderDate,
                                    onDelete = {
                                        deleteEvent(eventWithTask.event)
                                        isEventDialogShown = false
                                    },
                                    onDismissRequest = { isEventDialogShown = false },
                                    updateEvent = {calendar ->
                                        updateEvent(eventWithTask.event, eventWithTask.task, calendar)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarComposable(
    modifier: Modifier = Modifier,
    selectedDay: CalendarDay,
    updateSelectedDate: (day: CalendarDay) -> Unit,
    updateYearMonth: (yearMonth: YearMonth) -> Unit,
    monthEvents: List<Event>
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(2) }
    val endMonth = remember { currentMonth.plusMonths(3) }
    val daysOfWeek = remember { daysOfWeek(DayOfWeek.MONDAY) }

    val state = rememberCalendarState(
        startMonth,
        endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
        outDateStyle = OutDateStyle.EndOfGrid
    )

    LaunchedEffect(state.firstVisibleMonth.yearMonth) {
        updateYearMonth(state.firstVisibleMonth.yearMonth)
    }

    CompositionLocalProvider {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("MonthTitle")
                        .padding(15.dp)
                    ,
                    text = "" +
                            "${state.firstVisibleMonth.yearMonth.month} " +
                            "${state.firstVisibleMonth.yearMonth.year}",
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            HorizontalCalendar(
                modifier = Modifier.wrapContentWidth(),
                state = state,
                dayContent = {
                    val hasEvent = monthEvents.find {event -> event.dateKey == it.date.toString() } != null
                    Log.d("DKDK", hasEvent.toString())
                    Day(
                        day = it,
                        isSelected = it == selectedDay,
                        select = updateSelectedDate,
                        hasEvent = hasEvent
                    )
                },
                monthHeader = {
                    MonthHeader(daysOfWeek = daysOfWeek)
                },
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    select: (day: CalendarDay) -> Unit,
    hasEvent: Boolean
) {
    // day.date.
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(color = MaterialTheme.colorScheme.onSecondary)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { select(day) }
            )
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
            )
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.Center)
        ) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    color = if (day.position == DayPosition.MonthDate)
                        MaterialTheme.colorScheme.onBackground  else Color.Gray
                )
        }
        if (hasEvent) Card(
            modifier = Modifier.size(20.dp)
                .clip(RoundedCornerShape(50.dp))
                .padding(top = 5.dp, start = 5.dp)
        ) {}
    }
}

@Composable
@Preview(showBackground = true)
private fun DayPreview() {
    Day(CalendarDay(date = LocalDate.now(), position = DayPosition.MonthDate), isSelected = false, select = {}, hasEvent = true)
}

@Composable
private fun MonthHeader(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek> = emptyList(),
) {
    Row(
        modifier
            .fillMaxWidth()
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}



@Preview
@Composable
fun BottomSheetComposablePreview() {
    SheetComposable(selectedDay = LocalDate.now(), tasks = listOf(), deleteEvent = {}, updateEvent = {
            _: Event, _: Task, _: Calendar ->
    })
}