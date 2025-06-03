package com.example.todolist.domain.usecase

import com.example.todolist.domain.model.Task
import com.example.todolist.domain.repository.TaskRepository

class GetTaskByIdUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int): Task? {
        return repository.getTaskById(id)
    }
}
