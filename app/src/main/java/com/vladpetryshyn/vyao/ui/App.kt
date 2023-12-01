package com.vladpetryshyn.vyao.ui

import AddEventScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vladpetryshyn.vyao.ChangeNavigationColor
import com.vladpetryshyn.vyao.R
import com.vladpetryshyn.vyao.ui.calendar.CalendarScreen
import com.vladpetryshyn.vyao.ui.notebooks.NotebooksScreen
import com.vladpetryshyn.vyao.ui.settings.SettingsNavigation
import com.vladpetryshyn.vyao.ui.tasks.TasksScreen
import com.vladpetryshyn.vyao.ui.updateTask.UpdateTaskScreen


sealed class Screens(val route: String) {
    object Notebooks : Screens("Notebooks")
    object Calendar : Screens("Calendar")
    object Tasks : Screens("Tasks?notebookTitle={notebookTitle}")
    object Todo: Screens("Todo?notebookTitle={notebookTitle}")
    object UpdateTask: Screens("UpdateTask/{notebookTitle}/{taskId}")
    object CreateTask: Screens("UpdateTask/{notebookTitle}")
    object AddEvent: Screens("AddEvent?date={date}&taskId={taskId}")
    object Settings: Screens("Settings")
}


// App Ui entry point
@Composable
@ExperimentalMaterial3Api
fun VyaoApp(
    navController: NavHostController = rememberNavController(),
    viewModel: AppViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val bottomBarTabs = listOf(Screens.CreateTask.route, Screens.UpdateTask.route, Screens.AddEvent.route, Screens.Settings.route)
    val showBottomMenu = navBackStackEntry?.destination?.route !in bottomBarTabs
    val showTopMenu = showBottomMenu && navBackStackEntry?.destination?.route != Screens.Calendar.route

    val todoNotebook = viewModel.todoNotebook.collectAsState().value

    Scaffold(
        bottomBar =
        {
            AnimatedVisibility(visible = showBottomMenu) {
                AppBottomNavigation(
                    route = navBackStackEntry?.destination?.route,
                    navigate = navController::navigate
                )
                if (showBottomMenu) {
                    ChangeNavigationColor(MaterialTheme.colorScheme.inverseOnSurface)
                } else {
                    ChangeNavigationColor(MaterialTheme.colorScheme.surface)
                }
            }
        },
        topBar = {
            if (navController.currentBackStackEntry != null) {
                AnimatedVisibility(
                    visible = showTopMenu
                ) {
                    VyaoAppTopAppBar(
                        if (
                            navController.currentBackStackEntry!!.destination.route!! == Screens.Todo.route
                            ||
                            navController.currentBackStackEntry!!.destination.route!! == Screens.Tasks.route
                            ) {
                            navController.currentBackStackEntry!!.arguments!!["notebookTitle"].toString()
                        } else if (navController.currentBackStackEntry!!.destination.route!! == Screens.Notebooks.route) {
                            stringResource(R.string.notebooks)
                        } else {
                            ""
                        },
                        goToSettings = {navController.navigate(Screens.Settings.route)}
                    )
                }
            }
        }
    ) {
        padding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Todo.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(
                route = Screens.Todo.route,
                arguments = listOf(navArgument("notebookTitle") {defaultValue=todoNotebook ?: ""; type = NavType.StringType})
            ) {
                TasksScreen(
                    goToTaskCreate = {navController.navigate("UpdateTask/$it")},
                    goToTaskUpdate = {
                            notebookTitle: String, taskId: Int ->
                        navController.navigate("UpdateTask/$notebookTitle/$taskId")
                    }
                )
            }
            composable(route = Screens.Notebooks.route) {
                NotebooksScreen(
                    goToTasks = {title:String -> navController.navigate("Tasks?notebookTitle=$title")}
                )
            }
            composable(
                route = Screens.Tasks.route,
            ) {
                TasksScreen(
                    goToTaskCreate = {navController.navigate("UpdateTask/$it")},
                    goToTaskUpdate = {
                            notebookTitle: String, taskId: Int ->
                                navController.navigate("UpdateTask/$notebookTitle/$taskId")
                    }
                )
            }
            composable(
                route = Screens.UpdateTask.route,
                arguments = listOf(navArgument("taskId") {nullable = true})
            ) {
                UpdateTaskScreen(
                    navigateBack = navController::popBackStack,
                    goToAddEvent = {navController.navigate("AddEvent?taskId=$it")}
                )
            }
            composable(
                route = Screens.CreateTask.route,
            ) {
                UpdateTaskScreen(
                    navigateBack = navController::popBackStack,
                    goToAddEvent = {}
                )
            }
            composable(route = Screens.Calendar.route) {
                CalendarScreen(
                    navigateBack = navController::popBackStack,
                    addEvent = {navController.navigate("AddEvent?date=$it")}
                )
            }
            composable(route = Screens.AddEvent.route) {
                AddEventScreen(
                    navigateBack = navController::popBackStack,
                    goToTaskCreate = {navController.navigate("UpdateTask/$todoNotebook")},
                    isTodoSelected = todoNotebook != "" && todoNotebook != null
                )
            }
            composable(route = Screens.Settings.route) {
                SettingsNavigation(
                    navigateBackToApp = {navController.popBackStack()}
                )
            }
        }
    }
}

@Composable
fun AppBottomNavigation(
    route: String?,
    navigate: (route: String) -> Unit
) {
    NavigationBar {
        BottomNavigationItem().bottomNavigationItems()
            .forEach {
                item ->
                NavigationBarItem(
                    selected = route == item.route,
                    onClick = { navigate(item.route) },
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = stringResource(id = item.label)
                        )
                    },
                    label = {
                        Text(
                                stringResource(id = item.label)
                        )
                        }
                )
            }
    }
}

//initializing the data class with default parameters
data class BottomNavigationItem(
    val label : Int = 0,
    val icon : ImageVector = Icons.Filled.Done,
    val route : String = ""
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = R.string.notebooks,
                icon = Icons.Outlined.List,
                route = Screens.Notebooks.route
            ),
            BottomNavigationItem(
                label = R.string.todo,
                icon = Icons.Outlined.Done,
                route = Screens.Todo.route
            ),
            BottomNavigationItem(
                label = R.string.calendar,
                icon = Icons.Outlined.DateRange,
                route = Screens.Calendar.route
            ),
        )
    }
}
