package com.vladpetryshyn.vyao.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladpetryshyn.vyao.repositories.dataStore.DataStoreRepository
import com.vladpetryshyn.vyao.ui.settings.LookAndFeelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    val isJustBlackSelected: StateFlow<Boolean> = dataStoreRepository.isJustBlackSelected
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )
    val useMaterialColors: StateFlow<Boolean> = dataStoreRepository.useMaterialColors
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = true
        )
    val selectedThemeColor: StateFlow<String> = dataStoreRepository.themeColor
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LookAndFeelViewModel.selectedColor
        )
}