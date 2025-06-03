package com.example.todolist.domain.usecase

import com.example.todolist.domain.model.Task
import com.example.todolist.domain.repository.TaskRepository

class AddTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        repository.insertTask(task)
    }
}
