package com.vladpetryshyn.vyao.ui.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vladpetryshyn.vyao.R
import com.vladpetryshyn.vyao.getVersionName

enum class SettingsScreens(@StringRes val title: Int) {
    Settings(title = R.string.settings_screen_title),
    LookAndFeel(title = R.string.look_and_feel_settings_screen_title)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsNavigation(
    navController: NavHostController = rememberNavController(),
    navigateBackToApp: () -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState().value
    val currentScreen = SettingsScreens.valueOf(
        backStackEntry?.destination?.route
            ?:
            SettingsScreens.Settings.name)

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(currentScreen.title)) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (navController.currentBackStackEntry?.destination?.route
                                ==
                                SettingsScreens.Settings.name) {
                                navigateBackToApp()
                            } else {
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
    )
    {
        NavHost(
            navController = navController,
            startDestination = SettingsScreens.Settings.name,
            modifier = Modifier.padding(it)
        ) {
            composable(SettingsScreens.Settings.name) {
                SettingsScreen(
                    navigateToLookAndFeel={navController.navigate(SettingsScreens.LookAndFeel.name)}
                )
            }
            composable(SettingsScreens.LookAndFeel.name) {
                LookAndFeelScreen()
            }
        }
    }
}

@Composable
fun SettingsScreen(
    navigateToLookAndFeel: () -> Unit
) {
    var isFocusedModalShown by remember {mutableStateOf(false)}

    val settingsItems = listOf(
        SettingsMenuItemData(
            text = "Look and feel",
            icon = Icons.Default.Palette,
            onClick = navigateToLookAndFeel
        ),
        SettingsMenuItemData(
            text = "Info",
            icon = Icons.Default.Info,
            onClick = {isFocusedModalShown = true}
        ),
    )

    LazyColumn {
        items(settingsItems) {
            SettingsMenuItem(
                text = it.text,
                icon = it.icon,
                onClick = it.onClick
            )
        }
    }

    if (isFocusedModalShown) {
        InfoModal {
            isFocusedModalShown = false
        }
    }
}


@Composable
fun InfoModal(
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Filled.Info, contentDescription = null) },
        title = {
            Text(text = stringResource(id = R.string.info))
        },
        text = {
            val versionName = getVersionName(context)
            Text(stringResource(id = R.string.version, versionName))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(stringResource(id = R.string.ok))
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