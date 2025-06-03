package com.example.todolist.data.repository

import com.example.todolist.data.local.TaskDao
import com.example.todolist.domain.model.Task
import com.example.todolist.domain.model.toDomain
import com.example.todolist.domain.model.toEntity
import com.example.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> {
        return dao.getAllTasks().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)?.toDomain()
    }

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task.toEntity())
    }

    override suspend fun deleteAllTasks() {
        dao.deleteAllTasks()
    }
}
