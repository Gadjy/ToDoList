package com.example.todolist.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolist.presentation.components.TaskCard
import com.example.todolist.presentation.navigation.Screen
import com.example.todolist.presentation.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TaskListScreen(
    navController: NavController,
    viewModel: TaskViewModel
) {
    val taskList by viewModel.tasks.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.clearCurrentTask()
                navController.navigate(Screen.AddEditTask.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Мои задачи") }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(taskList) { task ->
                TaskCard(
                    task = task,
                    onClick = {
                        navController.navigate("${Screen.AddEditTask.route}?taskId=${task.id}")
                    },
                    viewModel = viewModel,
                    currentTime = currentTime
                )
            }
        }
    }
}
