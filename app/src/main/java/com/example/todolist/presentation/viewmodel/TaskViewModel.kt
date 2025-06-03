package com.example.todolist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.model.Task
import com.example.todolist.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val UseCases: UseCases
) : ViewModel() {
    private val _currentTime = MutableStateFlow(System.currentTimeMillis())
    val currentTime: StateFlow<Long> = _currentTime.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                _currentTime.value = System.currentTimeMillis()
                delay(1000L)
            }
        }
    }

    // Список всех задач
    val tasks: StateFlow<List<Task>> = UseCases.getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Текущая задача (для редактирования)
    private val _currentTask = MutableStateFlow<Task?>(null)
    val currentTask: StateFlow<Task?> = _currentTask.asStateFlow()

    fun addTask(task: Task) {
        viewModelScope.launch {
            UseCases.addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            UseCases.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            UseCases.deleteTask(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            UseCases.deleteAllTasks()
        }
    }

    fun loadTask(taskId: Int) {
        viewModelScope.launch {
            val task = UseCases.getTaskById(taskId)
            _currentTask.value = task
        }
    }

    fun clearCurrentTask() {
        _currentTask.value = null
    }
}
