package com.vladpetryshyn.vyao.ui.updateTask

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vladpetryshyn.vyao.ChangeNavigationColor
import com.vladpetryshyn.vyao.R


@Composable
@ExperimentalMaterial3Api
fun UpdateTaskScreen(
    updateTaskViewModel: UpdateTaskViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    goToAddEvent: (taskId: String) -> Unit,
) {
    val formDescription = updateTaskViewModel.formDescription.collectAsState().value
    val formTitle = updateTaskViewModel.formTitle.collectAsState().value
    val taskId = updateTaskViewModel.taskId

    Scaffold(
        topBar = {
            UpdateTaskTopAppBar(
                saveTask = {
                    if (
                        formDescription.isNotEmpty()
                        ||
                        formTitle.isNotEmpty()) {
                        updateTaskViewModel.createTask()
                    }
                    navigateBack()
                },
                navigateBack = navigateBack
            )
        },
        bottomBar = {
            if (taskId != null) {
                BottomAppBar(
                    actions = {
                        if (taskId != null) {
                            IconButton(onClick = { goToAddEvent(taskId) }) {
                                Icon(Icons.Default.DateRange, contentDescription = "")
                            }
                            ChangeNavigationColor(MaterialTheme.colorScheme.surfaceVariant)
                        }
                    }
                )
            }
        }
    ) {
        val scrollStateVertical = rememberScrollState()

        Column(
            modifier = Modifier.padding(it)
                .verticalScroll(state = scrollStateVertical)
        ) {
            CustomTextField(
                value = formTitle,
                placeholderText = stringResource(id = R.string.update_task_text_title),
                fontSize = 25,
                onValueChange = updateTaskViewModel::updateTitle,
                singleLine = false
            )
            CustomTextField(
                value = formDescription,
                placeholderText = stringResource(id = R.string.update_task_text_description),
                modifier = Modifier.fillMaxHeight(),
                singleLine = false,
                onValueChange = updateTaskViewModel::updateDescription
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun UpdateTaskTopAppBar(
    saveTask: () -> Unit,
    navigateBack: () -> Unit
) {
    var isSaveButtonEnabled by remember {
        mutableStateOf(true)
    }

    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    isSaveButtonEnabled = false
                    saveTask()
                },
                enabled = isSaveButtonEnabled
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                )
            }
        }
    )
}

@Composable
@ExperimentalMaterial3Api
fun CustomTextField(
    value: String,
    placeholderText: String,
    singleLine: Boolean = true,
    fontSize: Int = 20,
    modifier: Modifier = Modifier,
    onValueChange: (str: String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        modifier = modifier
            .fillMaxWidth(),
        textStyle = TextStyle(
            fontSize = fontSize.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = {
            innerTextField ->
            Box(
                modifier = Modifier
                    .padding(13.dp)
            ) {
                if (value.isEmpty())
                    Text(
                        placeholderText,
                        fontSize = fontSize.sp,
                        color = Color.LightGray
                    )
                innerTextField()
            }
        }
    )
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(value = "no sex", placeholderText ="sex", onValueChange = {} )
}
