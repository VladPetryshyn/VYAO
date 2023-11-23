package com.vladpetryshyn.vyao.ui.notebooks

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vladpetryshyn.vyao.R
import com.vladpetryshyn.vyao.repositories.room.notebooks.Notebook
import com.vladpetryshyn.vyao.ui.ActionDialog
import com.vladpetryshyn.vyao.ui.ActionDialogItem
import java.util.Date

@ExperimentalMaterial3Api
@Composable
fun NotebooksScreen(
    notebooksViewModel: NotebooksScreenViewModel = hiltViewModel(),
    goToTasks: (it: String) -> Unit
) {
    val notebooks = notebooksViewModel.notebooks.collectAsState()
    var isNewNotebookModalOpen by remember {mutableStateOf(false)}
    var isNotebookActionModalOpen by remember {mutableStateOf(false)}
    var selectedNotebook by remember { mutableStateOf<Notebook?>(null) }

    val newNotebookModalDismiss = {
        isNewNotebookModalOpen = !isNewNotebookModalOpen
        notebooksViewModel.updateNewNotebookTitleField("")
        notebooksViewModel.clearFormState()
    }
    val notebookActionModalDismiss = {
        isNotebookActionModalOpen = !isNotebookActionModalOpen
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = newNotebookModalDismiss) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        NotebooksList(
            notebooks = notebooks.value,
            modifier = Modifier.padding(it),
            goToTasks = goToTasks,
            openActionDialog = { notebook ->
                isNotebookActionModalOpen = true
                selectedNotebook = notebook
            }
        )
        if (isNewNotebookModalOpen) {
            NewNotebookModal(
                onDismiss = newNotebookModalDismiss,
                currentValue = notebooksViewModel.newNotebookTitleField,
                onChange = notebooksViewModel::updateNewNotebookTitleField,
                addNotebook = {
                    notebooksViewModel.insertNotebook(newNotebookModalDismiss)
                },
                formState = notebooksViewModel.formState
            )
        }
        if (isNotebookActionModalOpen) {
            NotebookActionsModal(
                onDismissRequest = notebookActionModalDismiss,
                notebook = selectedNotebook,
                onDelete = notebooksViewModel::deleteNotebook,
                makeTodo = notebooksViewModel::makeTodo
            )
        }
    }
}

@Composable
fun NotebooksList(
    notebooks: List<Notebook>,
    modifier: Modifier = Modifier,
    goToTasks: (it: String) -> Unit,
    openActionDialog: (task: Notebook) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(notebooks) {
            NotebookCard(
                notebook = it,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                goToTasks = goToTasks,
                openActionDialog = openActionDialog
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotebookCard(
    notebook: Notebook,
    modifier: Modifier = Modifier,
    goToTasks: (it: String) -> Unit,
    openActionDialog: (task: Notebook) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { goToTasks(notebook.title) },
                onLongClick = { openActionDialog(notebook) }
            )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = notebook.title,
                    style = MaterialTheme.typography.headlineLarge
                )
                if (notebook.isTodoNotebook) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(35.dp)
                    )
                }
            }
            Text(
                text = notebook.updated.toString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun NewNotebookModal(
    onDismiss: () -> Unit,
    currentValue: String,
    onChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    addNotebook: () -> Unit,
    formState: FormStates
) {
    val isError = formState != FormStates.Default
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = currentValue,
                    onValueChange = onChange,
                    label = {
                        Text("New notebook name")
                    },
                    isError = isError,
                    supportingText = {
                        if (isError) {
                            Text(
                                text = stringResource(id = formState.message),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.End)
                ) {
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    OutlinedButton(
                        onClick = addNotebook,
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

@Composable
fun NotebookActionsModal(
    onDismissRequest: () -> Unit,
    notebook: Notebook?,
    onDelete: (title: String) -> Unit,
    makeTodo: (title: String) -> Unit,
) {
    var isDeleteNotebookModalOpen by remember {mutableStateOf(false)}

    ActionDialog(onDismissRequest = onDismissRequest) {
        if (notebook != null && !notebook.isTodoNotebook) {
            ActionDialogItem(
                onClick = {
                    makeTodo(notebook.title)
                    onDismissRequest()
                          },
                text = stringResource(id = R.string.notebook_action_make_notebook),
                icon = Icons.Default.List
            )
        }
        ActionDialogItem(
            onClick = { isDeleteNotebookModalOpen = true },
            text = stringResource(id = R.string.notebook_action_delete_notebook),
            icon = Icons.Default.Delete
        )
    }

    if (isDeleteNotebookModalOpen) {
        DeleteDialog(
            onDismiss = {
                isDeleteNotebookModalOpen = false },
            onDelete = {
                if (notebook != null) {
                    onDelete(notebook.title)
                    onDismissRequest()
                } else {
                    Log.e("NotebooksScreen", "notebook is null")
                }
            },
            notebookTitle = notebook?.title ?: "Null"
        )
    }
}

@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    notebookTitle: String,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Filled.Delete, contentDescription = null) },
        title = {
            Text(text = stringResource(id = R.string.notebook_action_delete_notebook))
        },
        text = {
            Text(
                stringResource(id = R.string.notebook_action_delete_notebook_description, notebookTitle)
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDelete()
                    onDismiss()
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


@ExperimentalMaterial3Api
@Preview
@Composable
fun NewNotebookModalPreview() {
    NewNotebookModal(
        onDismiss = { /*TODO*/ },
        currentValue = "",
        onChange = {},
        addNotebook = {},
        formState = FormStates.Default
    )
}

@Preview
@Composable
fun NotebookCardPreview() {
    NotebookCard(notebook = Notebook(title = "Karlik", updated = Date()), goToTasks = {}, openActionDialog = {})
}

@Preview
@Composable
fun NotebookActionModal() {
    NotebookActionsModal(
        onDismissRequest = {},
        notebook = null,
        onDelete = {},
        makeTodo = {}
    )
}
