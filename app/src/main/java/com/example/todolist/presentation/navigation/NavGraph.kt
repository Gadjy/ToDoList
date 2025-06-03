package com.example.todolist.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todolist.presentation.screens.AddEditTaskScreen
import com.example.todolist.presentation.screens.TaskListScreen
import com.example.todolist.presentation.viewmodel.TaskViewModel

sealed class Screen(val route: String) {
    object TaskList : Screen("task_list")
    object AddEditTask : Screen("add_edit_task")

    fun withArgs(vararg args: Any?): String {
        return buildString {
            append(route)
            args.forEach { append("?taskId=$it") }
        }
    }
}




@Composable
fun NavGraph(
    navController: NavHostController
) {
    val viewModel: TaskViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.TaskList.route
    ) {
        composable(route = Screen.TaskList.route) {
            TaskListScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = "${Screen.AddEditTask.route}?taskId={taskId}",
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")?.takeIf { it != -1 }
            AddEditTaskScreen(
                navController = navController,
                viewModel = viewModel,
                taskId = taskId
            )
        }
    }
}

