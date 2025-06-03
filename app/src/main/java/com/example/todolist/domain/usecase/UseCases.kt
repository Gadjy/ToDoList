package com.example.todolist.domain.usecase

data class UseCases(
    val addTask: AddTaskUseCase,
    val getAllTasks: GetAllTasksUseCase,
    val getTaskById: GetTaskByIdUseCase,
    val updateTask: UpdateTaskUseCase,
    val deleteTask: DeleteTaskUseCase,
    val deleteAllTasks: DeleteAllTasksUseCase
)
