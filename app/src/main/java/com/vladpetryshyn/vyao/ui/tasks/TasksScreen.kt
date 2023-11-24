package com.vladpetryshyn.vyao.ui.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vladpetryshyn.vyao.R
import com.vladpetryshyn.vyao.pxToDp
import com.vladpetryshyn.vyao.repositories.room.events.Event
import com.vladpetryshyn.vyao.repositories.room.notebooks.Notebook
import com.vladpetryshyn.vyao.repositories.room.tasks.Task
import com.vladpetryshyn.vyao.spToPx
import com.vladpetryshyn.vyao.ui.ActionDialog
import com.vladpetryshyn.vyao.ui.ActionDialogItem
import com.vladpetryshyn.vyao.ui.EventDialog
import com.vladpetryshyn.vyao.ui.ItemFinderDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
@ExperimentalMaterial3Api
fun TasksScreen(
    tasksViewModel: TasksViewModel = hiltViewModel(),
    goToTaskCreate: (title: String) -> Unit,
    goToTaskUpdate: (title: String, taskId: Int) -> Unit
) {
    val tasks = tasksViewModel.tasks.collectAsState().value
    val todoNotSelected = tasks.isEmpty() && tasksViewModel.notebookTitle == ""

    Scaffold(
        floatingActionButton = {
            if (!todoNotSelected) {
                FloatingActionButton(onClick = { goToTaskCreate(tasksViewModel.notebookTitle) }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        },
    ) {
        if (todoNotSelected) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = stringResource(id = R.string.todo_select_notebook))
            }
        } else if (tasks.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(stringResource(id = R.string.task_notebook_empty))
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                items(tasks) { task ->
                    TaskCard(
                        task = task.task,
                        events = task.events,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        navigateToEdit = {
                            goToTaskUpdate(tasksViewModel.notebookTitle, task.task.id)
                        },
                        markAsDone = tasksViewModel::markTaskAsDone,
                        onDelete = tasksViewModel::deleteTask,
                        deleteEvent = tasksViewModel::deleteEvent,
                        updateEvent = tasksViewModel::updateEvent,
                        searchForNotebooks = tasksViewModel::searchForNotebooks,
                        notebooks = tasksViewModel.notebooksSearchItems.collectAsState().value,
                        moveTask = tasksViewModel::moveTask
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    task: Task,
    modifier: Modifier = Modifier,
    navigateToEdit: () -> Unit,
    events: List<Event>,
    onDelete: (taskId: Int) -> Unit,
    markAsDone: (taskId: Int) -> Unit,
    deleteEvent: (event: Event) -> Unit,
    updateEvent: (
        event: Event,
        task: Task,
        calendar: Calendar
    ) -> Unit,
    searchForNotebooks: (searchQuery: String) -> Unit,
    notebooks: List<Notebook>,
    moveTask: (notebookTitle: String, taskId: Int) -> Unit,
) {
    var isActionModalOpen by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = { isActionModalOpen = true },
                onClick = { navigateToEdit() }
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            val textDecoration = if (task.isDone) {
                TextDecoration.LineThrough
            } else {
                null
            }

            if (task.title.isNotEmpty()) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = textDecoration
                )
            }
            if (task.description.isNotEmpty()) {
                Text(
                    text = task.description,
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = textDecoration
                )
            }
            if (events.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    items(events) { event ->
                        TaskEvent(
                            reminderDate = event.reminderDate.time.toString(),
                            deleteEvent = {
                                deleteEvent(event)
                            },
                            updateEvent = { calendar: Calendar ->
                                updateEvent(event, task, calendar)
                            },
                        )
                    }
                }
            }
        }
    }

    if (isActionModalOpen) {
        TaskActionDialog(
            taskTitle = task.title,
            onDismissRequest = { isActionModalOpen = false },
            markAsDone = {
                markAsDone(task.id)
                isActionModalOpen = false
            },
            onDelete = {
                onDelete(task.id)
                isActionModalOpen = false
            },
            searchForNotebooks = searchForNotebooks,
            notebooks = notebooks,
            moveTask = { notebookTitle ->
                moveTask(notebookTitle, task.id)
            }
        )
    }
}

@Composable
fun TaskActionDialog(
    taskTitle: String,
    onDismissRequest: () -> Unit,
    onDelete: () -> Unit,
    markAsDone: () -> Unit,
    searchForNotebooks: (searchQuery: String) -> Unit,
    notebooks: List<Notebook>,
    moveTask: (notebookTitle: String) -> Unit,
) {
    var isMoveDialogShown by remember {
        mutableStateOf(false)
    }

    ActionDialog(onDismissRequest = onDismissRequest) {
        ActionDialogItem(
            onClick = markAsDone,
            text = stringResource(id = R.string.task_action_mark_as_done),
            icon = Icons.Filled.Done
        )
        ActionDialogItem(
            onClick = onDelete,
            text = stringResource(id = R.string.task_action_delete, taskTitle),
            icon = Icons.Filled.Delete
        )
        ActionDialogItem(
            onClick = {
                isMoveDialogShown = true
                searchForNotebooks("")
                      },
            text = stringResource(id = R.string.task_action_move_to, taskTitle),
            icon = Icons.Filled.List
        )
    }

    if (isMoveDialogShown) {
        MoveTaskDialog(
            onDismissRequest = { isMoveDialogShown = false },
            searchForNotebooks = searchForNotebooks,
            notebooks = notebooks,
            moveTask = moveTask
        )
    }
}

@Composable
fun MoveTaskDialog(
    onDismissRequest: () -> Unit,
    searchForNotebooks: (searchQuery: String) -> Unit,
    notebooks: List<Notebook>,
    moveTask: (notebookTitle: String) -> Unit
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    ItemFinderDialog(
        onDismissRequest = onDismissRequest,
        searchQuery = searchQuery,
        updateSearchQuery = { newSearchQuery ->
            searchQuery = newSearchQuery
            searchForNotebooks(newSearchQuery)
        },
        labelText = stringResource(id = R.string.search_for_notebooks)
    ) {
        LazyColumn {
            items(notebooks) { notebook ->
                Card(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                        .clickable {
                            moveTask(notebook.title)
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text(
                            text = notebook.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun TaskEvent(
    reminderDate: String,
    deleteEvent: () -> Unit,
    updateEvent: (
        calendar: Calendar
    ) -> Unit,
) {
    val context = LocalContext.current
    // 12 - bodySmall font size
    val iconSize = pxToDp(spToPx(12.0.toFloat(), context), context)
    val formatter = SimpleDateFormat("HH:mm, yyyy-MM-dd")
    val date = Date(reminderDate.toLong())

    var isEventDialogShown by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable { isEventDialogShown = true }
    ) {
        Row(
            modifier =
            Modifier
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(iconSize.dp),
            )
            Text(
                text = formatter.format(date),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    if (isEventDialogShown) {
        EventDialog(
            date = date,
            onDelete = {
                deleteEvent()
                isEventDialogShown = false
            },
            onDismissRequest = { isEventDialogShown = false },
            updateEvent = updateEvent
        )
    }
}

@Preview
@Composable
fun TaskCardPreview() {
    TaskCard(
        task = Task(
            1010,
            "101010",
            "dk",
            "Yes yes yes yes",
            Date(),
            scheduledFor = Date(),
            timeSpent = 10
        ),
        navigateToEdit = {},
        events = listOf(
            Event(
                taskId = 1,
                workerId = "",
                reminderDate = Date(),
                dateKey = "",
                yearMonth = ""
            )
        ),
        onDelete = {},
        markAsDone = {},
        deleteEvent = {},
        updateEvent = { _: Event,
                        _: Task,
                        _: Calendar ->
        },
        searchForNotebooks = {},
        notebooks = listOf(),
        moveTask = { _: String, _: Int -> }
    )
}