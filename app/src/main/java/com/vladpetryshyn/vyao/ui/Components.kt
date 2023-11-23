package com.vladpetryshyn.vyao.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
@ExperimentalMaterial3Api
fun CustomDatePicker(
    modifier: Modifier = Modifier,
    state: DatePickerState = rememberDatePickerState(initialSelectedDateMillis = null),
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: (@Composable () -> Unit)?
) {
    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        content = {
            DatePicker(
                state = state
            )
        },
        confirmButton = confirmButton,
        dismissButton = dismissButton
    )
}

@Composable
fun CustomTimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun CustomTimePicker(
    modifier: Modifier = Modifier,
    state: TimePickerState = rememberTimePickerState(),
    onCancel: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    CustomTimePickerDialog(
        onCancel = onCancel,
        onConfirm = onConfirm,
        content = {
            TimePicker(
                state = state,
                modifier = modifier
            )
        }
    )
}

@Composable
fun ActionDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card {
            content()
        }
    }
}

@Composable
fun ActionDialogItem(
    onClick: () -> Unit,
    text: String,
    icon: ImageVector,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 7.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ItemFinderDialog(
    onDismissRequest: () -> Unit,
    searchQuery: String,
    updateSearchQuery: (query: String) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                )
            ,
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .height(400.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = updateSearchQuery,
                    label = {
                        Text("Search for Task...")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription =""
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Column(
                    modifier = Modifier.weight(1f)
                        .padding(top = 10.dp)
                ) {
                    content()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDialog(
    date: Date,
    onDelete: () -> Unit,
    onDismissRequest: () -> Unit,
    updateEvent: (
        calendar: Calendar
    ) -> Unit,
) {

    var isDateSelectOpen by remember {
        mutableStateOf(false)
    }
    var isTimeSelectOpen by remember {
        mutableStateOf(false)
    }
    var selectedDate:Long? by remember {
        mutableStateOf(date.time)
    }

    val initialCalendar = Calendar.getInstance()
    initialCalendar.time = date

    var selectedHour by remember {
        mutableStateOf(initialCalendar.get(Calendar.HOUR_OF_DAY))
    }
    var selectedMinute by remember {
        mutableStateOf(initialCalendar.get(Calendar.MINUTE))
    }

    val timePickerState = rememberTimePickerState(
        initialMinute = selectedMinute,
        initialHour = selectedHour
    )

    val formattingCalendar = Calendar.getInstance()
    formattingCalendar.time = Date(selectedDate ?: date.time)
    formattingCalendar.set(Calendar.HOUR_OF_DAY, selectedHour)
    formattingCalendar.set(Calendar.MINUTE, selectedMinute)

    val dateText = SimpleDateFormat("dd MMMM").format(formattingCalendar.time)
    val timeText = SimpleDateFormat("HH:mm").format(formattingCalendar.time)

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
        ) {
            Column(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
            ) {
                Text(
                    text = "Edit reminder",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(bottom = 3.dp)
                )
                EventDialogItem(
                    text = dateText,
                    onClick = {isDateSelectOpen = true}
                )
                EventDialogItem(
                    text = timeText,
                    onClick = {isTimeSelectOpen = true}
                )

                Row(
                    modifier = Modifier.padding(top = 3.dp)
                ) {
                    Row(modifier = Modifier.weight(1f)){}
                    TextButton(onClick = onDelete) {
                        Text("Delete")
                    }
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        updateEvent(formattingCalendar)
                        onDismissRequest()
                    }) {
                        Text("Save")
                    }
                }
            }
        }

        if (isTimeSelectOpen) {
            CustomTimePicker(
                onCancel = {isTimeSelectOpen = false},
                onConfirm = {
                    selectedHour = timePickerState.hour
                    selectedMinute = timePickerState.minute
                    isTimeSelectOpen = false
                },
                state = timePickerState
            )
        }
        if (isDateSelectOpen) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = selectedDate
            )
            CustomDatePicker(
                onDismissRequest = { isDateSelectOpen = false },
                confirmButton = {
                    Button(onClick = {
                        selectedDate = datePickerState.selectedDateMillis
                        isDateSelectOpen = false
                    }) {
                        Text(text = "Select")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {isDateSelectOpen = false}) {
                        Text(text = "Cancel")
                    }
                },
                state = datePickerState
            )
        }
    }
}

@Composable
fun EventDialogItem(
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(top = 5.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
            Row(modifier = Modifier.weight(1f)){}
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
        }
        Divider()
    }
}


@Composable
@ExperimentalMaterial3Api
@Preview
fun CustomTimePickerPreview() {
    CustomTimePicker()
}


@ExperimentalMaterial3Api
@Composable
@Preview
fun CustomDatePickerPreview() {
    CustomDatePicker(onDismissRequest = {}, confirmButton = {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "yes")
        }
    },
        dismissButton = {
            Button(onClick = { /*TODO*/ }) {
                Text("kd")
            }
        }
    )
}