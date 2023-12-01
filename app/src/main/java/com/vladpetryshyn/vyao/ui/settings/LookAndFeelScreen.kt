package com.vladpetryshyn.vyao.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.vladpetryshyn.vyao.ColorPickerDialog


@Composable
fun LookAndFeelScreen(
    viewModel: LookAndFeelViewModel = hiltViewModel()
) {
    val isJustBlackSelected = viewModel.isJustBlackSelected.collectAsState().value
    val useMaterialColors = viewModel.useMaterialColors.collectAsState().value
    val selectedThemeColor = viewModel.selectedThemeColor.collectAsState().value

    var isColorPickerDialogShown by remember { mutableStateOf(false) }

    val disableThemingOptions = !useMaterialColors
    Column {
        SettingsMenuItem(
            text = "Use Material Theme",
            icon = Icons.Default.ColorLens,
            onClick = viewModel::toggleUseMaterialColors)
        { Switch(checked = useMaterialColors, onCheckedChange = {viewModel.toggleUseMaterialColors()}) }
        SettingsMenuItem(
            text = "Just Black",
            icon = Icons.Default.InvertColors,
            onClick = viewModel::toggleIsJustBlackSelected,
            enabled = disableThemingOptions
        )
        { Switch(checked = isJustBlackSelected, onCheckedChange = {viewModel.toggleIsJustBlackSelected()}, enabled = disableThemingOptions) }
        SettingsMenuItem(
            text = "Accent Color",
            icon = Icons.Default.Colorize,
            enabled = disableThemingOptions,
            onClick = { isColorPickerDialogShown = true })
    }

    if (isColorPickerDialogShown) {
        ColorPickerDialog(
            initialColor = LookAndFeelViewModel.colorsMapReverse[selectedThemeColor]!!,
            colors = LookAndFeelViewModel.colorsList,
            onChoice = viewModel::setSelectedThemeColor,
            onDismiss={isColorPickerDialogShown = false}
        )
    }
}