package com.example.todolist.domain.usecase

import com.example.todolist.domain.model.Task
import com.example.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetAllTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.getAllTasks()
    }
}
