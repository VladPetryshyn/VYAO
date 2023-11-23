package com.vladpetryshyn.vyao.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladpetryshyn.vyao.repositories.dataStore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository
) : ViewModel() {
    val todoNotebook: StateFlow<String?> = dataStoreRepository.todoNotebook
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )
}

@HiltViewModel
class AppTopBarViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    val userAvatar: StateFlow<String?> = dataStoreRepository.userAvatar
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun updateUserAvatar(uri: Uri?) {
        if (uri != null) {
            viewModelScope.launch {
                dataStoreRepository.saveUserAvatar(uri)
            }
        }
    }
}
