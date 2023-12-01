package com.vladpetryshyn.vyao.ui.settings

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
class LookAndFeelViewModel @Inject constructor(
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
            initialValue = selectedColor
        )

    fun toggleIsJustBlackSelected() {
            viewModelScope.launch {
                dataStoreRepository.setIsJustBlack(!isJustBlackSelected.value)
            }
    }
    fun toggleUseMaterialColors() {
        viewModelScope.launch {
            dataStoreRepository.setUseMaterialColors(!useMaterialColors.value)
            if (useMaterialColors.value) {
                dataStoreRepository.setIsJustBlack(false)
            }
        }
    }

    fun setSelectedThemeColor(newColor: String) {
        viewModelScope.launch {
            dataStoreRepository.setThemeColor(colorsMap[newColor]!!)
        }
    }

    companion object {
        val colorsList = listOf("#ff0000", "#ffa500", "#ffff00", "#00ff00", "#0000ff", "#800080")
        const val RED = "red"
        const val ORANGE = "orange"
        const val YELLOW = "yellow"
        const val GREEN = "green"
        const val BLUE = "blue"
        const val PURPLE = "PURPLE"

        val colorsMap = mapOf<String, String>(
            "#ff0000" to RED,
            "#ffa500" to ORANGE,
            "#ffff00" to YELLOW,
            "#00ff00" to GREEN,
            "#0000ff" to BLUE,
            "#800080" to PURPLE
            )
        val colorsMapReverse = mapOf<String, String>(
             RED to "#ff0000" ,
             ORANGE to "#ffa500",
             YELLOW to "#ffff00",
             GREEN to "#00ff00" ,
             BLUE to "#0000ff",
             PURPLE to "#800080"
        )
        val selectedColor = "#0000ff"
    }
}
