package com.vladpetryshyn.vyao.ui.toDo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vladpetryshyn.vyao.repositories.room.notebooks.Notebook
import com.vladpetryshyn.vyao.repositories.room.notebooks.NotebooksRoomRepository
import com.vladpetryshyn.vyao.repositories.room.tasks.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    notebooksRoomRepository: NotebooksRoomRepository
) : ViewModel() {
     val task: StateFlow<Notebook?> = notebooksRoomRepository.getTodoNotebook()
         .stateIn(
             scope = viewModelScope,
             started = SharingStarted.WhileSubscribed(1_000),
             initialValue = null
         )
}