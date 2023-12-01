package com.vladpetryshyn.vyao.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.vladpetryshyn.vyao.ui.settings.LookAndFeelViewModel

@Composable
fun VYAOTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val dynamicColor = themeViewModel.useMaterialColors.collectAsState().value
    val isJustBlack = themeViewModel.isJustBlackSelected.collectAsState().value
    val selectedColor = themeViewModel.selectedThemeColor.collectAsState().value

    val useCustomTheme = !dynamicColor

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> when {
            isJustBlack -> createJustBlackColorScheme(primary = LookAndFeelViewModel.colorsMapReverse[selectedColor]!!)
            selectedColor == LookAndFeelViewModel.RED && useCustomTheme -> RedColorScheme.DarkColors
            selectedColor == LookAndFeelViewModel.ORANGE && useCustomTheme -> OrangeColorScheme.DarkColors
            selectedColor == LookAndFeelViewModel.YELLOW && useCustomTheme -> YellowColorScheme.DarkColors
            selectedColor == LookAndFeelViewModel.GREEN && useCustomTheme -> GreenColorScheme.DarkColors
            selectedColor == LookAndFeelViewModel.PURPLE && useCustomTheme -> PurpleColorScheme.DarkColors
            else -> BlueColorScheme.DarkColors
        }
        else -> when {
            selectedColor == LookAndFeelViewModel.RED && useCustomTheme -> RedColorScheme.LightColors
            selectedColor == LookAndFeelViewModel.ORANGE && useCustomTheme -> OrangeColorScheme.LightColors
            selectedColor == LookAndFeelViewModel.YELLOW && useCustomTheme -> YellowColorScheme.LightColors
            selectedColor == LookAndFeelViewModel.GREEN && useCustomTheme -> GreenColorScheme.LightColors
            selectedColor == LookAndFeelViewModel.PURPLE && useCustomTheme -> PurpleColorScheme.LightColors
            else -> BlueColorScheme.LightColors
        }
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
