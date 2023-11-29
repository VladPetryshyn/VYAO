import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.vladpetryshyn.vyao.R
import com.vladpetryshyn.vyao.convertMarkdownToSpannedText
import com.vladpetryshyn.vyao.databinding.FragmentTasksCardDescriptionBinding
import com.vladpetryshyn.vyao.repositories.room.tasks.Task
import com.vladpetryshyn.vyao.ui.CustomDatePicker
import com.vladpetryshyn.vyao.ui.CustomTimePicker
import com.vladpetryshyn.vyao.ui.ItemFinderDialog
import com.vladpetryshyn.vyao.ui.addEvent.AddEventViewModel
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    viewModel: AddEventViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    goToTaskCreate: () -> Unit,
    isTodoSelected: Boolean
) {
    val context = LocalContext.current
    var isDateModalOpen by remember {mutableStateOf(false)}
    var isTimeModalOpen by remember {mutableStateOf(false)}
    var isTaskModalOpen by remember {mutableStateOf(false)}

    val toggleIsDateModalOpen = {isDateModalOpen = !isDateModalOpen}
    val toggleIsTimeModalOpen = {isTimeModalOpen = !isTimeModalOpen}
    val toggleIsTaskModalOpen = {
        isTaskModalOpen = !isTaskModalOpen
        viewModel.updateSearchQuery("")
    }

    val noNotebookText = stringResource(id = R.string.no_notebook_text)
    val showToast = { Toast.makeText(context, noNotebookText, Toast.LENGTH_SHORT).show() }

    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else {
            mutableStateOf(true)
        }
    }
    var shouldRedirectToSettings by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(ActivityCompat.shouldShowRequestPermissionRationale(
                            context as Activity,
                            android.Manifest.permission.POST_NOTIFICATIONS))
        } else {
            mutableStateOf(false)
        }
    }
    
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted

            if (!isGranted && !ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    android.Manifest.permission.POST_NOTIFICATIONS)
                ) {
                shouldRedirectToSettings = true
            }
        }
    )
    
    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = {
            hasNotificationPermission = ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        }
    )

    if (!hasNotificationPermission) {
        NotificationPermissionDialog(
            onDismiss = navigateBack,
            onConfirm = {
                if (shouldRedirectToSettings) {
                    val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    intent.setData(Uri.parse("package:" + context.packageName))
                    val pendIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                    settingsLauncher.launch(IntentSenderRequest.Builder(pendIntent).build())
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.add_event_title),
                    )
                },
                navigationIcon =  {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.createReminder(navigateBack, showToast)
                    }) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            EventItem(
                title = R.string.add_event_select_date_title,
                subtitle = viewModel.selectDateFormatted,
                icon = painterResource(id = R.drawable.calendar_month_24px),
                onClick = toggleIsDateModalOpen
            )

            val selectedHour = viewModel.selectedHour
            val selectedMinutes = viewModel.selectedMinutes
            val selectTimeSubtitle = "${if(selectedHour>9) {selectedHour}else {"0$selectedHour"}}" +
                    ":" +
                    "${if(selectedMinutes>9) {selectedMinutes}else {"0$selectedMinutes"}}"
            EventItem(
                icon = painterResource(R.drawable.schedule_24px),
                title = R.string.add_event_select_time_title,
                subtitle = selectTimeSubtitle,
                onClick = toggleIsTimeModalOpen
            )
            EventItem(
                icon = painterResource(id = R.drawable.task_alt_24px),
                title = R.string.add_event_select_task_title,
                subtitle = viewModel.selectedTask?.title
                    ?: stringResource(R.string.add_event_no_task_selected),
                onClick = toggleIsTaskModalOpen,
            )
        }
    }

    if (isDateModalOpen) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = TimeUnit.DAYS.toMillis(viewModel.selectedDate)
        )
        val onDateSelect: () -> Unit = {
            if (datePickerState.selectedDateMillis != null) {
                viewModel.updateSelectedDate(
                    TimeUnit.MILLISECONDS.toDays(datePickerState.selectedDateMillis!!)
                )
                toggleIsDateModalOpen()
            }
        }

        CustomDatePicker(
            onDismissRequest = toggleIsDateModalOpen,
            dismissButton = {
                TextButton(onClick = toggleIsDateModalOpen) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                TextButton(onClick = onDateSelect) {
                    Text(text = "OK")
                }
            },
            state = datePickerState
        )
    }
    if (isTimeModalOpen) {
        val timePickerState = rememberTimePickerState(
                initialHour = viewModel.selectedHour,
                initialMinute = viewModel.selectedMinutes,
        )

        val onTimeSelect: () -> Unit = {
            viewModel.updateSelectedTime(
                timePickerState.hour,
                timePickerState.minute
            )
            toggleIsTimeModalOpen()
        }
        CustomTimePicker(
            onCancel = toggleIsTimeModalOpen,
            state = timePickerState,
            onConfirm = onTimeSelect
        )
    }
    if (isTaskModalOpen) {
        val searchTasks = viewModel.taskItems.collectAsState().value

        TaskDialog(
            onDismiss = toggleIsTaskModalOpen,
            searchTasks = searchTasks,
            searchQuery = viewModel.searchQuery,
            updateSearchQuery = viewModel::updateSearchQuery,
            updateSelectedTask = viewModel::updateSelectedTask,
            goToTaskCreate = goToTaskCreate,
            isTodoSelected = isTodoSelected
        )
    }
}

@Composable
fun EventItem(
    icon: Painter,
    @StringRes title: Int,
    subtitle: String,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .clickable(
                onClick = onClick
            )
            .fillMaxWidth()
        ,
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 15.dp)
                    .size(40.dp)
            )
            Column {
                Text(
                    text = stringResource(title),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun TaskDialog(
    onDismiss: () -> Unit,
    searchTasks: List<Task>,
    searchQuery: String,
    updateSearchQuery: (t: String) -> Unit,
    updateSelectedTask: (t: Task) -> Unit,
    goToTaskCreate: () -> Unit,
    isTodoSelected: Boolean
) {
    ItemFinderDialog(onDismissRequest = onDismiss, searchQuery = searchQuery, updateSearchQuery = updateSearchQuery) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(searchTasks) {
                Card(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                        .clickable {
                            updateSelectedTask(it)
                            onDismiss()
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text(
                            text = it.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        val textColor = MaterialTheme.colorScheme.onSurface
                        Row(modifier = Modifier.padding(top = 4.dp)) {
                            AndroidViewBinding(factory = FragmentTasksCardDescriptionBinding::inflate) {
                                this.taskCardDescription.apply {
                                    text = convertMarkdownToSpannedText(it.description)
                                    maxLines = 2
                                    setTextColor(textColor.toArgb())
                                }
                            }
                        }
                    }
                }
            }
        }
        if (searchTasks.isEmpty() && isTodoSelected) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    onDismiss()
                    goToTaskCreate()
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TaskDialogPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    )
    {
        TaskDialog(
            onDismiss = {},
            searchTasks = listOf(),
            searchQuery = "",
            updateSearchQuery = {},
            updateSelectedTask = {},
            goToTaskCreate = {},
            isTodoSelected = false
        )
    }
}

@Composable
fun NotificationPermissionDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        icon = { Icon(Icons.Filled.Notifications, contentDescription = null) },
        title = {
            Text(text = stringResource(id = R.string.reminders_dialog_title))
        },
        text = {
            Text(
                stringResource(id = R.string.reminders_dialog_subtitle)
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text(stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(stringResource(id = R.string.dismiss))
            }
        }
    )
}