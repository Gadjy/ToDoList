package com.example.todolist.domain.usecase

import com.example.todolist.domain.repository.TaskRepository

class DeleteAllTasksUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllTasks()
    }
}
