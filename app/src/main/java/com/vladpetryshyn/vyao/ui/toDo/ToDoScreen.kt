package com.vladpetryshyn.vyao.ui.toDo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.vladpetryshyn.vyao.R

@Composable
fun ToDoScreen(
    toDoViewModel: ToDoViewModel = hiltViewModel(),
    goToTasks: (title: String) -> Unit
) {
    val task = toDoViewModel.task.collectAsState().value

    if (task == null) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.todo_select_notebook))
        }
    } else {
        goToTasks(task.title)
    }
}