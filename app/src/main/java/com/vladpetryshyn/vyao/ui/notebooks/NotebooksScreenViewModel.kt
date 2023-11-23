package com.vladpetryshyn.vyao.ui.notebooks

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.vladpetryshyn.vyao.VyaoApplication
import com.vladpetryshyn.vyao.repositories.room.notebooks.Notebook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import com.vladpetryshyn.vyao.R
import com.vladpetryshyn.vyao.repositories.dataStore.DataStoreRepository
import com.vladpetryshyn.vyao.repositories.room.notebooks.NotebooksRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotebooksScreenViewModel @Inject constructor(
    private val notebooksRepository: NotebooksRoomRepository,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    var formState by mutableStateOf(FormStates.Default)
    private set

    var newNotebookTitleField by mutableStateOf("")
    private set

    fun updateNewNotebookTitleField(value: String) {
        newNotebookTitleField = value
    }


    val notebooks: StateFlow<List<Notebook>> = notebooksRepository
        .getNotebooks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )

//    val notebooksTitles: StateFlow<List<String>> = notebooksRepository
//        .getNotebooksTitles()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = listOf()
//        )

    fun insertNotebook(modalDismiss: () -> Unit) {
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    val filteredTitle = newNotebookTitleField.filter { !it.isWhitespace() }
                    if (filteredTitle != "") {
                        notebooksRepository.insertNotebook(
                            Notebook(
                                title = newNotebookTitleField,
                                updated = Date()
                            )
                        )
                        modalDismiss()
                    } else {
                        formState = FormStates.FieldIsEmpty
                    }
            } catch (e: SQLiteConstraintException) {
                formState = FormStates.AlreadyExists
            }
            }
    }

    fun clearFormState() {
        formState = FormStates.Default
    }

    fun deleteNotebook(title: String) {
        viewModelScope.launch {
            notebooksRepository.deleteNotebook(title)
            dataStoreRepository.saveTodoNotebook("")
        }
    }

    fun makeTodo(title: String) {
        viewModelScope.launch(Dispatchers.Default) {
            notebooksRepository.deleteTodo()
            notebooksRepository.makeTodo(title)
            dataStoreRepository.saveTodoNotebook(title)
        }
    }
}

enum class FormStates(
    @StringRes
    val message: Int
) {
    Default(0),
    FieldIsEmpty(R.string.notebooks_add_form_empty_field_error),
    AlreadyExists(R.string.notebooks_add_form_already_exists_error),
}

fun CreationExtras.vyaoApplication(): VyaoApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VyaoApplication)